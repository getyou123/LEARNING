package org.example;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.flink_training.StateLearn.WaterSensorListState;

import org.example.flink_training.pojo.WaterSensor;
import org.junit.Test;


public class WaterSensorListStateTest {

    /**
     * 通过listState记录三个最高的水位线
     */
    @Test
    public void WaterSensorListStateTest001() {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment().setParallelism(1);

            SingleOutputStreamOperator<WaterSensor> stream = env
                    .socketTextStream("localhost", 9999)  // 在socket终端只输入毫秒级别的时间戳
                    .map(value -> {
                        String[] datas = value.split(",");
                        return new WaterSensor(datas[0], Long.valueOf(datas[1]), Integer.valueOf(datas[2]));
                    });

            stream.keyBy(WaterSensor::getId)
                    .process(new WaterSensorListState())
                    .print("最新高的三个水位");

            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
