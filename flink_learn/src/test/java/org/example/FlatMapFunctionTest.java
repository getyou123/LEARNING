package org.example;

import jline.WindowsTerminal;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.example.helloworld.FlatMapFunctionLearn.MyFlatMapFuntion;
import org.example.helloworld.MapFunctionLearn.MyRichMapFunction;
import org.example.helloworld.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试使用 FlatMapFunction
 */
public class FlatMapFunctionTest {
    @Test
    public void FlatMapFunctionTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .flatMap(new MyFlatMapFuntion()).setParallelism(2) // 从这里看到每个mapFunction是在每个并行度上来执行的
                .print();
        env.execute();
    }


    /**
     * 匿名内部类的方式实现
     *
     * @throws Exception
     */
    @Test
    public void FlatMapFunctionTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .flatMap(new FlatMapFunction<Integer, Integer>() {
                    @Override
                    public void flatMap(Integer integer, Collector<Integer> collector) throws Exception {
                        collector.collect(integer);
                        collector.collect(integer * 2);
                    }
                })
                .setParallelism(2)
                .print();
        env.execute();
    }


    /**
     * 使用lambada表达式
     * @throws Exception
     */
    @Test
    public void FlatMapFunctionTest003() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(x -> x.getVc()) // lambada 表达式的方式
                .flatMap((Integer value, Collector<Integer> out) -> {
                    out.collect(value);
                    out.collect(value * 2);
                })
                .returns(Types.INT) // 这里必须配置返回类型 泛型擦除的存在 全部当做Object来处理, 及其低效, 所以Flink要求当参数中有泛型的时候, 必须明确指定泛型的类型
                .setParallelism(2) // 从这里看到每个mapFunction是在每个并行度上来执行的
                .print();
        env.execute();
    }
}
