package org.example.helloworld.StateLearn;

import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;


/**
 * 每个key一个记录
 */
public class WaterSensorMapStateLearn extends KeyedProcessFunction<String, WaterSensor, WaterSensor> {
    private MapState<Integer, String> mapState;

    @Override
    public void open(Configuration parameters) throws Exception {
        mapState = this
                .getRuntimeContext()
                .getMapState(new MapStateDescriptor<Integer, String>("mapState", Integer.class, String.class));
    }


    /**
     * 不存存在过水位线就产出，否则不输出
     */
    @Override
    public void processElement(WaterSensor value, Context ctx, Collector<WaterSensor> out) throws Exception {
        if (!mapState.contains(value.getVc())) {
            out.collect(value);
            mapState.put(value.getVc(), "随意");
        }
    }
}
