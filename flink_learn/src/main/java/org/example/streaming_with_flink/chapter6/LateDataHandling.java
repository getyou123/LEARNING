package org.example.streaming_with_flink.chapter6;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.scala.typeutils.Types;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.example.streaming_with_flink.util.SensorReading;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.streaming_with_flink.util.SensorSource;
import org.example.streaming_with_flink.util.SensorTimeAssigner;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.Random;

/**
 * 处理迟到数据的几种方式
 */
public class LateDataHandling {

    // define a side output tag
    public static final OutputTag<SensorReading> lateReadingsOutput = new OutputTag<>("late-readings");

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // checkpoint every 10 seconds
        env.getCheckpointConfig().setCheckpointInterval(10 * 1000);

        // use event time for the application
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // configure watermark interval
        env.getConfig().setAutoWatermarkInterval(500L);

        // ingest sensor stream and shuffle timestamps to produce out-of-order records
        DataStream<SensorReading> outOfOrderReadings = env
                // SensorSource generates random temperature readings
                .addSource(new SensorSource())
                // shuffle timestamps by max 7 seconds to generate late data
                .map(new TimestampShuffler(7 * 1000))
                // assign timestamps and watermarks with an offset of 5 seconds
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        // Different strategies to handle late records.
        // Select and uncomment one of the lines below to demonstrate a strategy.

        // 1. Filter out late readings (to a side output) using a ProcessFunction
        filterLateReadings(outOfOrderReadings);
        // 2. Redirect late readings to a side output in a window operator
        sideOutputLateEventsWindow(outOfOrderReadings);
        // 3. Update results when late readings are received in a window operator
        updateForLateEventsWindow(outOfOrderReadings);

        env.execute();
    }

    /**
     * @param readings
     */
    private static void updateForLateEventsWindow(DataStream<SensorReading> readings) {
        SingleOutputStreamOperator<Tuple4<String, Long, Integer, String>> countPer10Secs =
                readings
                        .keyBy(r -> r.id)
                        .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                        .process(new UpdatingWindowCountFunction());

        // print results
        countPer10Secs
                .print();
    }


    /**
     * Count reading per tumbling window and emit late readings to a side output.
     * Print results and late events.
     */
    private static void sideOutputLateEventsWindow(DataStream<SensorReading> readings) {
        SingleOutputStreamOperator<Tuple3<String, Long, Integer>> countPer10Secs = readings
                .keyBy(r -> r.id)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .sideOutputLateData(lateReadingsOutput) // 从这里获取迟到数据
                .allowedLateness(Time.seconds(3))// 允许迟到时间
                .process(new ProcessWindowFunction<SensorReading, Tuple3<String, Long, Integer>, String, TimeWindow>() {
                    @Override
                    public void process(String id,
                                        ProcessWindowFunction<SensorReading, Tuple3<String, Long, Integer>, String, TimeWindow>.Context context,
                                        Iterable<SensorReading> elements,
                                        Collector<Tuple3<String, Long, Integer>> out) throws Exception {
                        int cnt = 0;
                        for (SensorReading element : elements) {
                            cnt++;
                        }
                        out.collect(Tuple3.of(id, context.window().getEnd(), cnt));
                    }
                });

        // retrieve and print messages for late readings
        countPer10Secs.getSideOutput(lateReadingsOutput)
                .map(r -> "*** late reading *** " + r.id)
                .print();


        countPer10Secs.print();
    }

    /**
     * 这个方法过滤出
     * Filter late readings to a side output and print the on-time and late streams.
     *
     * @param readings
     */
    public static void filterLateReadings(DataStream<SensorReading> readings) {
        // re-direct late readings to the side output
        SingleOutputStreamOperator<SensorReading> filteredReadings = readings
                .process(new LateReadingsFilter());

        // retrieve late readings
        DataStream<SensorReading> lateReadings = filteredReadings
                .getSideOutput(lateReadingsOutput);

        // print the filtered stream
        filteredReadings.print();

        // print messages for late readings
        lateReadings
                .map(r -> "*** late reading *** " + r.id)
                .print();
    }

}

/**
 * 在这里处理放到侧输出流
 * A ProcessFunction that filters out late sensor readings and re-directs them to a side output
 */
class LateReadingsFilter extends ProcessFunction<SensorReading, SensorReading> {

    @Override
    public void processElement(SensorReading r,
                               ProcessFunction<SensorReading, SensorReading>.Context ctx,
                               Collector<SensorReading> out
    ) throws Exception {
        // compare record timestamp with current watermark
        if (r.timestamp < ctx.timerService().currentWatermark()) {
            // this is a late reading => redirect it to the side output
            ctx.output(LateDataHandling.lateReadingsOutput, r);
        } else {
            out.collect(r);
        }

    }
}


/**
 * A MapFunction to shuffle (up to a max offset) the timestamps of SensorReadings to produce out-of-order events.
 */

class TimestampShuffler implements MapFunction<SensorReading, SensorReading> {

    private int maxRandomOffset;

    Random rand = new Random();

    TimestampShuffler(int maxRandomOffset) {
        this.maxRandomOffset = maxRandomOffset;
    }

    @Override
    public SensorReading map(SensorReading r) throws Exception {
        long shuffleTs = r.timestamp + rand.nextInt(maxRandomOffset);
        return new SensorReading(r.id, shuffleTs, r.temperature);
    }
}

/**
 * A counting WindowProcessFunction that distinguishes between first results and updates.
 */
class UpdatingWindowCountFunction extends ProcessWindowFunction<SensorReading, Tuple4<String, Long, Integer, String>, String, TimeWindow> {

    @Override
    public void process(String id,
                        ProcessWindowFunction<SensorReading, Tuple4<String, Long, Integer, String>, String, TimeWindow>.Context context,
                        Iterable<SensorReading> elements,
                        Collector<Tuple4<String, Long, Integer, String>> out) throws Exception {
        int cnt = 0;
        for (SensorReading element : elements) {
            cnt++;
        }
        ValueState<Boolean> isUpdate = context.windowState().getState(new ValueStateDescriptor<Boolean>("isUpdate", Types.BOOLEAN()));

        if (!isUpdate.value()) {
            // first evaluation, emit first result
            out.collect(Tuple4.of(id, context.window().getEnd(), cnt, "first"));
            isUpdate.update(true);
        } else {
            // not the first evaluation, emit an update
            out.collect(Tuple4.of(id, context.window().getEnd(), cnt, "update"));
        }

    }
}