package org.example.flink_training.exercise03;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.example.flink_training.entity.TaxiFare;
import org.example.flink_training.entity.TaxiRide;
import org.example.flink_training.source.TaxiRideGenerator;
import org.example.flink_training.source.TaxiFareGenerator;


/**
 * 实现获取每个小时内的tips最大的司机的id
 */

public class HourlyTipsExercise {

    private final SourceFunction<TaxiFare> source;
    private final SinkFunction<Tuple3<Long, Long, Float>> sink;

    /**
     * Creates a job using the source and sink provided.
     */
    public HourlyTipsExercise(SourceFunction<TaxiFare> fareSource, SinkFunction<Tuple3<Long, Long, Float>> sink) {
        this.source = fareSource;
        this.sink = sink;
    }

    public JobExecutionResult execute() throws Exception {

        // set up streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // start the data generator and arrange for watermarking
        DataStream<TaxiFare> fares =
                env.addSource(source)
                        .assignTimestampsAndWatermarks( // 设置时间戳和WatermarkStrategy
                                // taxi fares are in order
                                WatermarkStrategy.<TaxiFare>forMonotonousTimestamps()
                                        .withTimestampAssigner(
                                                (fare, t) -> fare.getEventTimeMillis()
                                        )
                        );

        // compute tips per hour for each driver
        DataStream<Tuple3<Long, Long, Float>> hourlyTips =
                fares.keyBy((TaxiFare fare) -> fare.driverId)
                        .window(TumblingEventTimeWindows.of(Time.hours(1)))
                        .process(new AddTips());


        // find the driver with the highest sum of tips for each hour

        /* 方案一： 实现按照事件时间每个小时一个数据输出 */
        DataStream<Tuple3<Long, Long, Float>> hourlyMax =
                hourlyTips.windowAll(TumblingEventTimeWindows.of(Time.hours(1))).maxBy(2);

        /* 方案二： 每条数据一个数据，数据量比每个小时更大 */
//        DataStream<Tuple3<Long, Long, Float>> hourlyMax = hourlyTips
//                .keyBy(t -> t.f0)
//                .maxBy(2);

        /* You should explore how this alternative (commented out below) behaves.
         * In what ways is the same as, and different from, the solution above (using a windowAll)?
         */

        // DataStream<Tuple3<Long, Long, Float>> hourlyMax = hourlyTips.keyBy(t -> t.f0).maxBy(2);

        hourlyMax.addSink(sink);

        // execute the transformation pipeline
        return env.execute("Hourly Tips");

    }


    /**
     * Wraps the pre-aggregated result into a tuple along with the window's timestamp and key.
     * 输入数据类型 TaxiFare
     * 输出数据类型 Tuple3 <Long, Long, Float>
     * 窗口键值类型-这个示例就是司机的id 所以key是long的类型
     * 窗口
     */
    public static class AddTips extends ProcessWindowFunction<TaxiFare, Tuple3<Long, Long, Float>, Long, TimeWindow> {

        @Override
        public void process(
                Long key,
                Context context, // 上下文对象
                Iterable<TaxiFare> fares,
                Collector<Tuple3<Long, Long, Float>> out) {

            // 这里窗口内的局部变量，所以不需要使用状态
            float sumOfTips = 0F;
            for (TaxiFare f : fares) {
                sumOfTips += f.tip;
            }
            // 返回窗口结束时间点，司机id 这个窗口内的司机的总的tips和
            out.collect(Tuple3.of(context.window().getEnd(), key, sumOfTips));
        }
    }


    /**
     * Main method.
     *
     * @throws Exception which occurs during job execution.
     */
    public static void main(String[] args) throws Exception {

        HourlyTipsExercise job =
                new HourlyTipsExercise(new TaxiFareGenerator(), new PrintSinkFunction<>());

        job.execute();
    }
}
