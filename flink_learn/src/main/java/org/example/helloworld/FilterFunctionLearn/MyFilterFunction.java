package org.example.helloworld.FilterFunctionLearn;

import org.apache.flink.api.common.functions.FilterFunction;

/**
 * 实现过滤
 */
public class MyFilterFunction implements FilterFunction<Integer> {
    @Override
    public boolean filter(Integer integer) throws Exception {
        return integer > 44;
    }
}
