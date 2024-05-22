package org.example.streaming_with_flink.chapter8;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.example.streaming_with_flink.util.SensorReading;
import org.example.streaming_with_flink.util.SensorTimeAssigner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TransactionSinkExample {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // checkpoint every 10 seconds
        env.getCheckpointConfig().setCheckpointInterval(10 * 1000);

        // use event time for the application
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // configure watermark interval
        env.getConfig().setAutoWatermarkInterval(1000L);

        // ingest sensor stream
        DataStream<SensorReading> sensorData = env
                // SensorSource generates random temperature readings
                .addSource(new ResettableSensorSource())
                // assign timestamps and watermarks which are required for event time
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        // compute average temperature of all sensors every second

        SingleOutputStreamOperator<Tuple2<String, Double>> apply = sensorData
                .windowAll(TumblingEventTimeWindows.of(Time.minutes(1)))
                .apply((window, values, out) -> {
                    double sum = 0.0d;
                    int size = 0;
                    for (SensorReading value : values) {
                        sum += value.getTemperature();
                        size++;
                    }
                    double avg = sum / size;
                    // format window timestamp as ISO timestamp string
                    long epochSeconds = window.getEnd() / 1000;
                    String tString = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC)
                            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    // emit record
                    out.collect(Tuple2.of(tString, avg));
                });

        DataStream<Tuple2<String, Double>> avgTemp = apply
                // generate failures to trigger job recovery
                .map(new FailingMapper<Tuple2<String, Double>>(16))
                .setParallelism(1);

        // OPTION 1 (comment out to disable)
        // --------
        // write to files with a transactional sink.
        // results are committed when a checkpoint is completed.
        Tuple2<String, String> paths = createAndGetPaths();
        avgTemp.addSink(new TransactionalFileSink(paths.f0, paths.f1));

        // OPTION 2 (uncomment to enable)
        // --------
        // print to standard out without write-ahead log.
        // results are printed as they are produced and re-emitted in case of a failure.
//        avgTemp.print()
//                // enforce sequential writing
//                .setParallelism(1);

        env.execute();
    }

    /**
     * Creates temporary paths for the output of the transactional file sink.
     */
    private static Tuple2<String, String> createAndGetPaths() {
        String tempDir = System.getProperty("java.io.tmpdir");
        String targetDir = tempDir + "/committed";
        String transactionDir = tempDir + "/transaction";

        Path targetPath = Paths.get(targetDir);
        Path transactionPath = Paths.get(transactionDir);

        try {
            if (!Files.exists(targetPath)) {
                Files.createDirectory(targetPath);
            }
            if (!Files.exists(transactionPath)) {
                Files.createDirectory(transactionPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary directories", e);
        }

        return new Tuple2<>(targetDir, transactionDir);
    }

    /**
     * Transactional sink that writes records to files an commits them to a target directory.
     * <p>
     * Records are written as they are received into a temporary file. For each checkpoint, there is
     * a dedicated file that is committed once the checkpoint (or a later checkpoint) completes.
     */
    private static class TransactionalFileSink extends TwoPhaseCommitSinkFunction<Tuple2<String, Double>, String, Void> {
        private transient BufferedWriter transactionWriter;

        public TransactionalFileSink(String targetPath, String tempPath) {
            super(
                    TypeInformation.of(String.class).createSerializer(new ExecutionConfig()),
                    TypeInformation.of(Void.class).createSerializer(new ExecutionConfig()));
            this.targetPath = targetPath;
            this.tempPath = tempPath;
        }

        private final String targetPath;
        private final String tempPath;


        /**
         * Creates a temporary file for a transaction into which the records are
         * written.
         */
        @Override
        public String beginTransaction() throws Exception {
            // path of transaction file is constructed from current time and task index
            String timeNow = LocalDateTime.now(ZoneId.of("UTC"))
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            int taskIdx = getRuntimeContext().getIndexOfThisSubtask();
            String transactionFile = timeNow + "-" + taskIdx;

            // create transaction file and writer
            Path tFilePath = Paths.get(tempPath, transactionFile);
            Files.createFile(tFilePath);
            this.transactionWriter = Files.newBufferedWriter(tFilePath);
            System.out.println("Creating Transaction File: " + tFilePath);

            // name of transaction file is returned to later identify the transaction
            return transactionFile;
        }

        /**
         * Write record into the current transaction file.
         */
        @Override
        public void invoke(String transaction, Tuple2<String, Double> value, Context context) throws Exception {
            transactionWriter.write(value.toString());
            transactionWriter.newLine();
        }

        /**
         * Flush and close the current transaction file.
         */
        @Override
        public void preCommit(String transaction) throws Exception {
            transactionWriter.flush();
            transactionWriter.close();
        }

        /**
         * Commit a transaction by moving the pre-committed transaction file
         * to the target directory.
         */
        @Override
        public void commit(String transaction) {
            Path tFilePath = Paths.get(tempPath, transaction);
            // check if the file exists to ensure that the commit is idempotent.
            if (Files.exists(tFilePath)) {
                Path cFilePath = Paths.get(targetPath, transaction);
                try {
                    Files.move(tFilePath, cFilePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /**
         * Aborts a transaction by deleting the transaction file.
         */
        @Override
        public void abort(String transaction) {
            Path tFilePath = Paths.get(tempPath, transaction);
            if (Files.exists(tFilePath)) {
                try {
                    Files.delete(tFilePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

/**
 * 只用来展示fail
 *
 * @param <IN>
 */
class FailingMapper<IN> implements MapFunction<IN, IN> {

    private final int failInterval;
    private int cnt;

    public FailingMapper(int failInterval) {
        this.failInterval = failInterval;
    }

    @Override
    public IN map(IN value) throws Exception {
        cnt++;
        // check failure condition
        if (cnt > failInterval) {
            throw new RuntimeException("Fail application to demonstrate output consistency.");
        }
        // forward value
        return value;
    }
}