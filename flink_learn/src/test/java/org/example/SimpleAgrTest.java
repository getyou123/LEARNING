package org.example;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SimpleAgrTest {

    /**
     * @throws Exception
     */
    @Test
    public void SimpleAgrTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(x -> x.getId())
                .min("vc")
                .print();
        env.execute();
    }


    @Test
    public void SimpleAgrTest002() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42),
                new WaterSensor("ws_003", 1577844020L, 41));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .keyBy(x -> x.getId())
                .max("vc")
                .print();
        env.execute();

        // @TODO
        //6> WaterSensor{id='ws_002', ts=1577844015, vc=43}
        //7> WaterSensor{id='ws_003', ts=1577844020, vc=42}
        //7> WaterSensor{id='ws_003', ts=1577844020, vc=42}
        //3> WaterSensor{id='ws_001', ts=1577844001, vc=45}

    }

    /**
     * 使用min max sum等聚合函数
     *
     * @throws Exception
     */
    @Test
    public void SimpleAgrTest003() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> stream = env.fromElements(1, 2, 3, 4, 5);
        KeyedStream<Integer, String> kbStream = stream
                .keyBy(ele -> ele % 2 == 0 ? "奇数" : "偶数");

        // 每个数据都输出一行结果
        kbStream.sum(0).print("sum");
        // sum:2> 2
        //sum:7> 1
        //sum:2> 6
        //sum:7> 4
        //sum:7> 9

        kbStream.max(0).print("max");
        //max:2> 2
        //max:7> 1
        //max:2> 4
        //max:7> 3
        //max:7> 5
        kbStream.min(0).print("min");
        //min:2> 2
        //min:7> 1
        //min:2> 2
        //min:7> 1
        //min:7> 1
        env.execute();
    }


    /**
     * 使用 minBy maxBy
     *
     * @throws Exception
     */
    @Test
    public void SimpleAgrTest004() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> stream = env.fromElements(1, 2, 3, 4, 5);
        KeyedStream<Integer, String> kbStream = stream
                .keyBy(ele -> ele % 2 == 0 ? "奇数" : "偶数");

        // 上游每个数据都驱动输出一行结果
        kbStream.maxBy(0).print("max");
        //max:7> 1
        //max:2> 2
        //max:7> 3
        //max:2> 4
        //max:7> 5

        kbStream.minBy(0).print("min");
        //min:7> 1
        //min:2> 2
        //min:7> 1
        //min:2> 2
        //min:7> 1
        env.execute();
    }


    /**
     * reduce方法 这里展示分组求和
     * reduce方法要求前后的数据类型是一致的
     */
    @Test
    public void SimpleAgrTest005() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> stream = env.fromElements(1, 2, 3, 4, 5);
        KeyedStream<Integer, String> kbStream = stream
                .keyBy(ele -> ele % 2 == 0 ? "奇数" : "偶数");

        // 上游每个数据都驱动输出一行结果
        SingleOutputStreamOperator<Integer> reduce = kbStream.reduce(new ReduceFunction<Integer>() {
            @Override
            public Integer reduce(Integer integer, Integer t1) throws Exception {
                return integer + t1;
            }
        });

        reduce.print("reduce");

        env.execute();
    }


}
