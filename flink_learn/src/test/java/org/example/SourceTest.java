package org.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.example.helloworld.SourceLearn.MySource;
import org.example.helloworld.pojo.WaterSensor;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SourceTest {
    /**
     * flink 从集合中读取数据
     */
    @Test
    public void sourceTest001() throws Exception {
        List<WaterSensor> waterSensors = Arrays.asList(
                new WaterSensor("ws_001", 1577844001L, 45),
                new WaterSensor("ws_002", 1577844015L, 43),
                new WaterSensor("ws_003", 1577844020L, 42));

        // 1. 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromCollection(waterSensors)
                .print();
        env.execute();
    }

    /**
     * flink 从文件中读取数据
     * 可以是本地file 可以是 hdfs等
     * 可以是路径也可是文件
     *
     * @throws Exception
     */
    @Test
    public void sourceTest002() throws Exception {
        String inPath = "";
        if (inPath.equals("")) {
            return;
        } else {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env
                    .readTextFile(inPath)
                    .print();

            env.execute();
        }
    }


    /**
     * 测试读取kafka中的数据
     *
     * @throws Exception
     */
    @Test
    public void sourceTest003() throws Exception {
        // 0.Kafka相关配置
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "Flink01_Source_Kafka");
        properties.setProperty("auto.offset.reset", "latest");

        // 1. 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .addSource(new FlinkKafkaConsumer<>("sensor", new SimpleStringSchema(), properties))
                .print("kafka source");
        env.execute();
    }


    /**
     * 测试读取自定义的source
     *
     * @throws Exception
     */
    @Test
    public void sourceTest004() throws Exception {
        try {
            // 1. 创建执行环境
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env
                    .addSource(new MySource("localhost", 7777))
                    .print("kafka source");
            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
