package org.example.streaming_with_flink.chapter6;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.example.streaming_with_flink.util.SensorReading;
import org.example.streaming_with_flink.util.SensorSource;
import org.example.streaming_with_flink.util.SensorTimeAssigner;

public class WindowFunctions {

    public static double threshold = 25.0;

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
                .addSource(new SensorSource())
                // assign timestamps and watermarks which are required for event time
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        DataStream<Tuple2<String, Double>> minTempPerWindow = sensorData
                .map(r -> new Tuple2<>(r.id, r.temperature))
                .keyBy(t -> t.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(15)))
                .reduce(new MinTempFunction());

        DataStream<Tuple2<String, Double>> avgTempPerWindow = sensorData
                .map(r -> new Tuple2<>(r.id, r.temperature))
                .keyBy(t -> t.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(15)))
                .aggregate(new AvgTempFunction());

        // output the lowest and highest temperature reading every 5 seconds
        DataStream<MinMaxTemp> minMaxTempPerWindow = sensorData
                .keyBy(SensorReading -> SensorReading.id)
                .window(TumblingEventTimeWindows.of(Time.seconds(15)))
                .process(new HighAndLowTempProcessFunction());

        DataStream<MinMaxTemp> minMaxTempPerWindow2 = sensorData
                .map(r -> new Tuple2<>(r.id, new Tuple2<>(r.temperature, r.temperature)))
                .keyBy(t -> t.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(15)))
                .reduce(
                        // incrementally compute min and max temperature
                        (r1, r2) -> new Tuple2<>(r1.f0, new Tuple2<>(Math.min(r1.f1.f0, r2.f1.f0), Math.max(r1.f1.f1, r2.f1.f1))),
                        // finalize result in ProcessWindowFunction
                        new AssignWindowEndProcessFunction()
                );

        // print result stream
        minMaxTempPerWindow2.print();

        env.execute();
    }


    private static class MinTempFunction implements ReduceFunction<Tuple2<String, Double>> {
        @Override
        public Tuple2<String, Double> reduce(Tuple2<String, Double> r1, Tuple2<String, Double> r2) throws Exception {
            return new Tuple2<>(r1.f0, Math.min(r1.f1, r2.f1));
        }
    }

    private static class AvgTempFunction implements AggregateFunction<Tuple2<String, Double>, Tuple3<String, Double, Integer>, Tuple2<String, Double>> {
        @Override
        public Tuple3<String, Double, Integer> createAccumulator() {
            return new Tuple3<>("", 0.0, 0);
        }

        @Override
        public Tuple3<String, Double, Integer> add(Tuple2<String, Double> value, Tuple3<String, Double, Integer> accumulator) {
            return new Tuple3<>(value.f0, accumulator.f1 + value.f1, accumulator.f2 + 1);
        }

        @Override
        public Tuple2<String, Double> getResult(Tuple3<String, Double, Integer> accumulator) {
            return new Tuple2<>(accumulator.f0, accumulator.f1 / accumulator.f2);
        }

        @Override
        public Tuple3<String, Double, Integer> merge(Tuple3<String, Double, Integer> a, Tuple3<String, Double, Integer> b) {
            return new Tuple3<>(a.f0, a.f1 + b.f1, a.f2 + b.f2);
        }
    }

    public static class MinMaxTemp {
        public final String id;
        public final double min;
        public final double max;
        public final long endTs;

        public MinMaxTemp(String id, double min, double max, long endTs) {
            this.id = id;
            this.min = min;
            this.max = max;
            this.endTs = endTs;
        }
    }

    /**
     * A ProcessWindowFunction that computes the lowest and highest temperature
     * reading per window and emits a them together with the
     * end timestamp of the window.
     */
    private static class HighAndLowTempProcessFunction extends ProcessWindowFunction<SensorReading, MinMaxTemp, String, TimeWindow> {
        @Override
        public void process(String key, Context context, Iterable<SensorReading> values, Collector<MinMaxTemp> out) throws Exception {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for (SensorReading reading : values) {
                min = Math.min(min, reading.temperature);
                max = Math.max(max, reading.temperature);
            }
            long windowEnd = context.window().getEnd();
            out.collect(new MinMaxTemp(key, min, max, windowEnd));
        }

    }

    private static class AssignWindowEndProcessFunction extends ProcessWindowFunction<Tuple2<String, Tuple2<Double, Double>>, MinMaxTemp, String, TimeWindow> {
        @Override
        public void process(String key, Context context, Iterable<Tuple2<String, Tuple2<Double, Double>>> values, Collector<MinMaxTemp> out) throws Exception {
            Tuple2<String, Tuple2<Double, Double>> minMax = values.iterator().next();
            long windowEnd = context.window().getEnd();
            out.collect(new MinMaxTemp(key, minMax.f1.f0, minMax.f1.f1, windowEnd));
        }
    }
}