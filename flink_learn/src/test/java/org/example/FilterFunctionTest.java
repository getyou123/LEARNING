package org.example;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.example.helloworld.FilterFunctionLearn.MyFilterFunction;
import org.example.helloworld.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FilterFunctionTest {

    /**
     * 匿名内部类实现
     *
     * @throws Exception
     */
    @Test
    public void FilterFunctionTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .filter(new FilterFunction<Integer>() {
                    @Override
                    public boolean filter(Integer integer) throws Exception {
                        return integer > 44;
                    }
                })
                .print();
        env.execute();
    }


    /**
     * lambada表达式方式 核心也是 FilterFunction
     *
     * @throws Exception
     */
    @Test
    public void FilterFunctionTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .filter(x -> x > 44)
                .print();
        env.execute();
    }


    /**
     * 自定义实现类 实现filter
     *
     * @throws Exception
     */
    @Test
    public void FilterFunctionTest003() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .filter(new MyFilterFunction())
                .print();
        env.execute();
    }
}
