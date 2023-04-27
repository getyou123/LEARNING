package org.example.helloworld.ProcessFunctionLearn;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;

/**
 * 这里是三个参数
 * key 类型
 * 数据类型
 * 返回值类型
 */
public class MyKeyedProcessFunction extends KeyedProcessFunction<String, WaterSensor, Integer> {

    @Override
    public void processElement(WaterSensor waterSensor, KeyedProcessFunction<String, WaterSensor, Integer>.Context context, Collector<Integer> collector) throws Exception {
        String currentKey = context.getCurrentKey();
        System.out.println("currentKey : " + currentKey); // 获取分区的key
        Integer vc = waterSensor.getVc();
        collector.collect(vc);
    }
}
