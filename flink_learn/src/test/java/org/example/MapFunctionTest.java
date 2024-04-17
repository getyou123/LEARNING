package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.flink_training.MapFunctionLearn.MyMapFunction;
import org.example.flink_training.MapFunctionLearn.MyRichMapFunction;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MapFunctionTest {


    /**
     * 使用自己的MapFunction
     *
     * @throws Exception
     */
    @Test
    public void MapFunctionTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        // 1. 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc())
                .map(new MyMapFunction())
                .print();
        env.execute();
    }


    /**
     * 测试使用map的并行度
     * @throws Exception
     */
    @Test
    public void MapFunctionTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .map(new MyRichMapFunction()).setParallelism(2) // 从这里看到每个mapFunction是在每个并行度上来执行的
                .print();
        env.execute();
    }
}
