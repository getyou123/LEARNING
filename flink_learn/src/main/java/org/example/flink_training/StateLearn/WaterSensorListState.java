package org.example.flink_training.StateLearn;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.flink_training.pojo.WaterSensor;

import java.util.ArrayList;
import java.util.List;

public class WaterSensorListState extends KeyedProcessFunction<String, WaterSensor, List<Integer>> {

    // 记录三个 最高的水位线
    private ListState<Integer> vcState;

    @Override
    public void open(Configuration parameters) throws Exception {
        vcState = getRuntimeContext().getListState(new ListStateDescriptor<Integer>("vcState", Integer.class));
    }

    @Override
    public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, List<Integer>>.Context ctx, Collector<List<Integer>> out) throws Exception {
        vcState.add(value.getVc());
        ArrayList<Integer> waterSensors = new ArrayList<>();
        for (Integer waterSensor : vcState.get()) {
            waterSensors.add(waterSensor);
        }

        waterSensors.sort((o1, o2) -> o2 - o1);
        if (waterSensors.size() > 3) {
            waterSensors.remove(3);
        }

        vcState.update(waterSensors);
        out.collect(waterSensors);
    }
}
