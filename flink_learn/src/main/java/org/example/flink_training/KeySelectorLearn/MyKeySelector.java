package org.example.flink_training.KeySelectorLearn;

import org.apache.flink.api.java.functions.KeySelector;
import org.example.flink_training.pojo.WaterSensor;


/**
 * 两个参数分别是输入参数和输出参数类型
 */
public class MyKeySelector implements KeySelector<WaterSensor, Integer> {

    @Override
    public Integer getKey(WaterSensor waterSensors) throws Exception {
        return waterSensors.getVc();
    }
}
