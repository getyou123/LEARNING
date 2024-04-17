package org.example;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SideOutStreamTest {
    @Test
    public void SideOutStreamTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<WaterSensor> result = env
                .fromCollection(waterSensors)
                .keyBy(ws -> ws.getTs())
                .process(new KeyedProcessFunction<Long, WaterSensor, WaterSensor>() {
                    @Override
                    public void processElement(WaterSensor value, Context ctx, Collector<WaterSensor> out) throws Exception {
                        out.collect(value);
                        if (value.getVc() > 5) { //水位大于5的写入到侧输出流
                            ctx.output(new OutputTag<WaterSensor>("警告") {
                            }, value);
                        }
                    }
                });

        result.print("主流");
        result.getSideOutput(new OutputTag<WaterSensor>("警告") {
        }).print("警告");
        env.execute();
    }
}
