package org.example;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;
import org.junit.Test;

public class TimerLearnTest {
    /**
     * 测试定时器
     * WaterSensor{id='ws_001', ts=1577844001, vc=45}
     * 1683800601368
     * 我被触发了....
     * WaterSensor{id='ws_001', ts=1577844001, vc=45}
     * 1683800614892
     * 我被触发了....
     */
    @Test
    public void TimerLearnTest001() {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(1);

            SingleOutputStreamOperator<WaterSensor> stream = env
                    .socketTextStream("localhost", 9999)  // 在socket终端只输入毫秒级别的时间戳
                    .map(value -> {
                        String[] datas = value.split(",");
                        return new WaterSensor(datas[0], Long.valueOf(datas[1]), Integer.valueOf(datas[2]));
                    });

            stream.keyBy(WaterSensor::getId)
                    .process(new KeyedProcessFunction<String, WaterSensor, String>() {
                        @Override
                        public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, String>.Context ctx, Collector<String> out) throws Exception {
                            // 处理时间过后5s后触发定时器
                            ctx.timerService().registerProcessingTimeTimer(ctx.timerService().currentProcessingTime() + 5000);
                            out.collect(value.toString());
                        }

                        // 定时器被触发之后, 回调这个方法
                        // 参数1: 触发器被触发的时间
                        @Override
                        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                            System.out.println(timestamp);
                            out.collect("我被触发了....");
                        }
                    })
                    .print();
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
