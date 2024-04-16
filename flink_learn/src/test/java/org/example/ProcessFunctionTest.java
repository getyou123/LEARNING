package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.example.flink_training.ProcessFunctionLearn.MyKeyedProcessFunction;
import org.example.flink_training.ProcessFunctionLearn.MyProcessFunction;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ProcessFunctionTest {
    /**
     * 使用processFunction方式来实现map方法
     *
     * @throws Exception
     */
    @Test
    public void ProcessFunctionTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .process(new MyProcessFunction())
                .print();
        env.execute();
    }


    /**
     * 使用processFunction方式来实现map方法
     */
    @Test
    public void ProcessFunctionTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .process(new ProcessFunction<WaterSensor, Integer>() {
                    @Override
                    public void processElement(WaterSensor waterSensor, ProcessFunction<WaterSensor, Integer>.Context context, Collector<Integer> collector) throws Exception {
                        collector.collect(waterSensor.getVc() * 2);
                    }
                })
                .print();
        env.execute();
    }


    /**
     * 使用processFunction方式来实现map方法
     */
    @Test
    public void ProcessFunctionTest003() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(WaterSensor::getId)
                .process(new MyKeyedProcessFunction())
                .print();
        env.execute();
    }


    /**
     * 使用匿名函数的keyProcessFunction
     *
     */
    @Test
    public void ProcessFunctionTest004() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(WaterSensor::getId)
                .process(new KeyedProcessFunction<String, WaterSensor, String>() {
                    @Override
                    public void processElement(WaterSensor waterSensor, KeyedProcessFunction<String, WaterSensor, String>.Context context, Collector<String> collector) throws Exception {
                        String currentKey = waterSensor.getVc() + context.getCurrentKey();
                        collector.collect(currentKey);
                    }
                })
                .print();
        env.execute();
    }

}
