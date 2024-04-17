package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.flink_training.SinkFunctionLearn.MySinkFunction;
import org.example.flink_training.SinkFunctionLearn.MySinkFunctionWithPool;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MySinkTest {

    /**
     * 测试使用mysql进行数据输出
     * 输出到mysql
     */
    @Test
    public void MySinkTest001() {
        try {
            List<WaterSensor> waterSensors = Arrays.asList(
                    new WaterSensor("ws_001", 1577844001L, 45),
                    new WaterSensor("ws_002", 1577844015L, 43),
                    new WaterSensor("ws_003", 1577844020L, 42),
                    new WaterSensor("ws_006", 1577844020L, 41));

            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env
                    .fromCollection(waterSensors)
                    .addSink(new MySinkFunction());
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 写出到mysql的sink
     * 使用druid来管理jdbc连接
     */
    @Test
    public void MySinkTest002() {
        try {
            List<WaterSensor> waterSensors = Arrays.asList(
                    new WaterSensor("ws_001", 1577844001L, 45),
                    new WaterSensor("ws_002", 1577844015L, 43),
                    new WaterSensor("ws_003", 1577844020L, 42),
                    new WaterSensor("ws_006", 1577844020L, 41));

            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env
                    .fromCollection(waterSensors)
                    .addSink(new MySinkFunctionWithPool());
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
