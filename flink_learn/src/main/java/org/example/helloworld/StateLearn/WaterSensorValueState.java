package org.example.helloworld.StateLearn;


import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;


/**
 * 实现
 * 检测传感器的水位值，如果连续的两个水位值超过10，就输出报警。
 */
public class WaterSensorValueState extends KeyedProcessFunction<String, WaterSensor, String> {
    private ValueState<WaterSensor> preState; // 前一个key的状态

    @Override
    public void open(Configuration parameters) throws Exception {
        preState = getRuntimeContext().getState(new ValueStateDescriptor<WaterSensor>("state", WaterSensor.class));
    }

    @Override
    public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, String>.Context ctx, Collector<String> out) throws Exception {
        Integer lastVc = preState.value() == null ? 0 : preState.value().getVc();

        if (Math.abs(value.getVc() - lastVc) >= 10) {
            out.collect(value.getId() + " 红色警报!!!");
        }

        preState.update(value);
    }
}
