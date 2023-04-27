package org.example.helloworld.FlatMapFunctionLearn;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class MyFlatMapFuntion implements FlatMapFunction<Integer, Integer> {

    /**
     * 一个对应多个的，也是按照并行度来分配的
     *
     * 这里实现 一个整数存储一个自身和一个2倍
     *
     * @param integer
     * @param collector
     * @throws Exception
     */
    @Override
    public void flatMap(Integer integer, Collector<Integer> collector) throws Exception {

        collector.collect(integer);
        collector.collect(integer * 2);

    }
}
