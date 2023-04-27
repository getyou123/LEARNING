package org.example;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.helloworld.FilterFunctionLearn.MyFilterFunction;
import org.example.helloworld.KeySelectorLearn.MyKeySelector;
import org.example.helloworld.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class KeySelectorTest {
    /**
     * 使用自定义的KeySelector
     */
    @Test
    public void KeySelectorTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(new MyKeySelector())
                .print();
        env.execute();
    }


    /**
     * lambada表达式
     *
     */
    @Test
    public void KeySelectorTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(x -> x.getVc())
                .print();
        env.execute();
    }


    /**
     * 匿名函数类
     *
     * @throws Exception
     */
    @Test
    public void KeySelectorTest003() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(new KeySelector<WaterSensor, Integer>() {
                    @Override
                    public Integer getKey(WaterSensor waterSensor) throws Exception {
                        return waterSensor.getVc();
                    }
                })
                .print();
        env.execute();
    }
}
