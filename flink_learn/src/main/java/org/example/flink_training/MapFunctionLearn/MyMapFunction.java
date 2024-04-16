package org.example.flink_training.MapFunctionLearn;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * 自定义的MapFunction
 */
public class MyMapFunction implements MapFunction<Integer, Integer> {
    @Override
    public Integer map(Integer integer) throws Exception {
        return integer * integer;
    }
}
