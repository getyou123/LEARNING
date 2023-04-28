package org.example;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.example.helloworld.WaterMarkLearn.MyWaterMarkGeneratorForWaterSersor;
import org.example.helloworld.pojo.WaterSensor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.junit.Test;


import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class WaterMarkLearnTest {

    /**
     * 这是从集合中获取数据的时间戳
     * 其实没到窗口改关闭的时候就关闭开始触发计算了
     *
     * @throws Exception
     * @TODO why
     */
    @Test
    public void WaterMarkLearnTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));


        // 创建水印生产策略
        WatermarkStrategy<WaterSensor> wms = WatermarkStrategy
                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3)) // 最大容忍的延迟时间
                .withTimestampAssigner(new SerializableTimestampAssigner<WaterSensor>() { // 指定时间戳
                    @Override
                    public long extractTimestamp(WaterSensor element, long recordTimestamp) {
                        return element.getTs() * 1000;
                    }
                });

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<String> process = env
                .fromCollection(waterSensors)
                .assignTimestampsAndWatermarks(wms)
                .keyBy(WaterSensor::getId)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .process(new ProcessWindowFunction<WaterSensor, String, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<WaterSensor> elements, Collector<String> out) throws Exception {
                        String msg = "当前key: " + key
                                + "窗口: [" + context.window().getStart() / 1000 + "," + context.window().getEnd() / 1000 + ") 一共有 "
                                + elements.spliterator().estimateSize() + "条数据 ";
                        out.collect(msg);
                    }
                });

        process.print();
        env.execute();
    }


    /**
     * 从socket中读取数据，可以更好的测试
     * 和理解水印
     * 使用 WatermarkStrategy的静态方法来构造
     */
    @Test
    public void WaterMarkLearnTest002() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> stream = env
                .socketTextStream("localhost", 9999)  // 在socket终端只输入毫秒级别的时间戳
                .map(value -> {
                    String[] datas = value.split(",");
                    return new WaterSensor(datas[0], Long.valueOf(datas[1]), Integer.valueOf(datas[2]));
                });


        // 创建水印生产策略-这里使用的是WatermarkStrategy中静态方法来构造
        WatermarkStrategy<WaterSensor> wms = WatermarkStrategy
                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3)) // 最大容忍的延迟时间
                .withTimestampAssigner(new SerializableTimestampAssigner<WaterSensor>() { // 指定时间戳
                    @Override
                    public long extractTimestamp(WaterSensor element, long recordTimestamp) {
                        return element.getTs() * 1000;
                    }
                });


        SingleOutputStreamOperator<String> process = stream
                .assignTimestampsAndWatermarks(wms)
                .keyBy(WaterSensor::getId)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .process(new ProcessWindowFunction<WaterSensor, String, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<WaterSensor> elements, Collector<String> out) throws Exception {
                        String msg = "当前key: " + key
                                + "窗口: [" + context.window().getStart() / 1000 + "," + context.window().getEnd() / 1000 + ") 一共有 "
                                + elements.spliterator().estimateSize() + "条数据 ";
                        out.collect(msg);
                    }
                });

        process.print();
        env.execute();
    }


    /**
     * 从socket中读取数据，可以更好的测试
     * 和理解水印完全的自定义使用
     * 自己构造一个WatermarkStrategy
     */
    @Test
    public void WaterMarkLearnTest003() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> stream = env
                .socketTextStream("localhost", 9999)  // 在socket终端只输入毫秒级别的时间戳
                .map(value -> {
                    String[] datas = value.split(",");
                    return new WaterSensor(datas[0], Long.valueOf(datas[1]), Integer.valueOf(datas[2]));
                });


        // 创建水印生产策略-这里自己new 一个WatermarkStrategy
        WatermarkStrategy<WaterSensor> wms = new WatermarkStrategy<WaterSensor>() {
            @Override
            public WatermarkGenerator<WaterSensor> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
                System.out.println("createWatermarkGenerator ....");
                return new MyWaterMarkGeneratorForWaterSersor(); // 这里返回了 WaterMarkGenerator
            }
        }.withTimestampAssigner(new SerializableTimestampAssigner<WaterSensor>() {
            @Override
            public long extractTimestamp(WaterSensor element, long recordTimestamp) {
                System.out.println("recordTimestamp  " + recordTimestamp);
                return element.getTs() * 1000;
            }
        });


        SingleOutputStreamOperator<String> process = stream
                .assignTimestampsAndWatermarks(wms)
                .keyBy(WaterSensor::getId)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .process(new ProcessWindowFunction<WaterSensor, String, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<WaterSensor> elements, Collector<String> out) throws Exception {
                        String msg = "当前key: " + key
                                + "窗口: [" + context.window().getStart() / 1000 + "," + context.window().getEnd() / 1000 + ") 一共有 "
                                + elements.spliterator().estimateSize() + "条数据 ";
                        out.collect(msg);
                    }
                });

        process.print();
        env.execute();
    }


    /**
     * @TODO
     * ws_001, 1577844001, 45
     * ws_002,1577844015,43
     * ws_003,1577844020,42
     * ws_003,1577844025,42
     * ws_002,1577844015,43
     * ws_001,1577844001,45
     * 为啥超时没有输出呢
     * @throws Exception
     */

    @Test
    public void WaterMarkLearnTest004() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(1);
        System.out.println(env.getConfig());

        SingleOutputStreamOperator<WaterSensor> stream = env
                .socketTextStream("localhost", 9999)  // 在socket终端只输入毫秒级别的时间戳
                .map(new MapFunction<String, WaterSensor>() {
                    @Override
                    public WaterSensor map(String value) throws Exception {
                        String[] datas = value.split(",");
                        return new WaterSensor(datas[0], Long.valueOf(datas[1]), Integer.valueOf(datas[2]));

                    }
                });

        // 创建水印生产策略
        WatermarkStrategy<WaterSensor> wms = WatermarkStrategy
                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3)) // 最大容忍的延迟时间
                .withTimestampAssigner(new SerializableTimestampAssigner<WaterSensor>() { // 指定时间戳
                    @Override
                    public long extractTimestamp(WaterSensor element, long recordTimestamp) {
                        return element.getTs() * 1000;
                    }
                });

        SingleOutputStreamOperator<String> result = stream
                .assignTimestampsAndWatermarks(wms)
                .keyBy(WaterSensor::getId)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .allowedLateness(Time.seconds(3))
                .sideOutputLateData(new OutputTag<WaterSensor>("side_1") {}) // 设置侧输出流
                .process(new ProcessWindowFunction<WaterSensor, String, String, TimeWindow>() {
                    @Override
                    public void process(String key, Context context, Iterable<WaterSensor> elements, Collector<String> out) throws Exception {
                        String msg = "当前key: " + key
                                + " 窗口: [" + context.window().getStart() / 1000 + "," + context.window().getEnd() / 1000 + ") 一共有 "
                                + elements.spliterator().estimateSize() + "条数据" +
                                "watermark: " + context.currentWatermark();
                        out.collect(context.window().toString());
                        out.collect(msg);
                    }
                });
        result.print("处理数据流");
        result.getSideOutput(new OutputTag<WaterSensor>("side_1") {}).print("超时");
        env.execute();
    }

}

