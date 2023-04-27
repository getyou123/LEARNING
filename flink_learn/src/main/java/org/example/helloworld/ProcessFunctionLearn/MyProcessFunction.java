package org.example.helloworld.ProcessFunctionLearn;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;

public class MyProcessFunction extends ProcessFunction<WaterSensor, Integer> {

    /**
     * 这个是每个数据都做一下处理调用
     *
     * @param waterSensor 输入数据类型
     * @param context     上下文对象
     * @param collector   下游collector
     */
    @Override
    public void processElement(WaterSensor waterSensor, ProcessFunction<WaterSensor, Integer>.Context context, Collector<Integer> collector) throws Exception {
        Integer id = waterSensor.getVc();
        int res = 2 * id + id % 12;
        collector.collect(res);
    }
}
