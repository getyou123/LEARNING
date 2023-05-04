package org.example.helloworld.StateLearn;

import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.helloworld.pojo.WaterSensor;

public class WaterSensorReducingStateLearn01 extends KeyedProcessFunction<String, WaterSensor, Double> {
    private ReducingState<Tuple2<Integer, Integer>> rSta;

    @Override
    public void open(Configuration parameters) throws Exception {
        ReducingStateDescriptor<Tuple2<Integer, Integer>> descriptor = new ReducingStateDescriptor<>(
                "avgState",
                (value1, value2) -> Tuple2.of(value1.f0 + value2.f0, value1.f1 + value2.f1),
                TypeInformation.of(new TypeHint<Tuple2<Integer, Integer>>() {
                })
        );

        rSta = getRuntimeContext()
                .getReducingState(descriptor);
    }


    @Override
    public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, Double>.Context ctx, Collector<Double> out) throws Exception {
        // add the current element to the reducing state
        rSta.add(new Tuple2<>(value.getVc(), 1));

        // get the current average
        Tuple2<Integer, Integer> sum = rSta.get();
        int count = sum.f1;
        int total = sum.f0;
        double avg = (double) total / count;

        // emit the output element
        out.collect(avg);
    }
}
