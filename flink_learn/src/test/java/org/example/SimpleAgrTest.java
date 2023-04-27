package org.example;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.helloworld.pojo.WaterSensor;
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

    @Test
    public void SimpleAgrTest003() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> stream = env.fromElements(1, 2, 3, 4, 5);
        KeyedStream<Integer, String> kbStream = stream
                .keyBy(ele -> ele % 2 == 0 ? "奇数" : "偶数");
        kbStream.print();
        kbStream.sum(0).print("sum");
        kbStream.max(0).print("max");
        kbStream.min(0).print("min");


    }

}
