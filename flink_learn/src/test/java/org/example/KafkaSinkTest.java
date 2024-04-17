package org.example;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;

import java.util.ArrayList;

public class KafkaSinkTest {


    /**
     * kafka作为sink端
     * 写出到kafka
     */
    @Test
    public void KafkaSinkTest001() throws Exception {
        ArrayList<WaterSensor> waterSensors = new ArrayList<>();
        waterSensors.add(new WaterSensor("sensor_1", 1607527992000L, 20));
        waterSensors.add(new WaterSensor("sensor_1", 1607527994000L, 50));
        waterSensors.add(new WaterSensor("sensor_1", 1607527996000L, 50));
        waterSensors.add(new WaterSensor("sensor_2", 1607527993000L, 10));
        waterSensors.add(new WaterSensor("sensor_2", 1607527995000L, 30));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .map(JSON::toJSONString)
                .addSink(new FlinkKafkaProducer<String>("localhost:9092", "topic_sensor", new SimpleStringSchema()))
                .setParallelism(1);
        env.execute();

    }
}
