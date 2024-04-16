package org.example.flink_training.StateLearn;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.flink_training.pojo.WaterSensor;

public class WaterSensorAggregatingStateLearn extends KeyedProcessFunction<String, WaterSensor, Double> {

    // 输入类型和输出类型
    private AggregatingState<Integer, Double> avgState;

    @Override
    public void open(Configuration parameters) throws Exception {

        AggregatingStateDescriptor<Integer, Tuple2<Integer, Integer>, Double> aggregatingStateDescriptor = new AggregatingStateDescriptor<>("avgState", new AggregateFunction<Integer, Tuple2<Integer, Integer>, Double>() {

            // 初始化
            @Override
            public Tuple2<Integer, Integer> createAccumulator() {
                return Tuple2.of(0, 0);
            }


            // add 操作
            @Override
            public Tuple2<Integer, Integer> add(Integer value, Tuple2<Integer, Integer> accumulator) {
                return Tuple2.of(accumulator.f0 + value, accumulator.f1 + 1);
            }

            // 获取getResult
            @Override
            public Double getResult(Tuple2<Integer, Integer> accumulator) {
                return accumulator.f0 * 1D / accumulator.f1;
            }

            // 分区之间如何合并状态
            @Override
            public Tuple2<Integer, Integer> merge(Tuple2<Integer, Integer> a, Tuple2<Integer, Integer> b) {
                return Tuple2.of(a.f0 + b.f0, a.f1 + b.f1);
            }
        }, Types.TUPLE(Types.INT, Types.INT));

        avgState = getRuntimeContext().getAggregatingState(aggregatingStateDescriptor);
    }

    @Override
    public void processElement(WaterSensor value, Context ctx, Collector<Double> out) throws Exception {
        avgState.add(value.getVc());
        out.collect(avgState.get());
    }
}
