package org.example.helloworld.MapFunctionLearn;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

/**
 * 这个map 可以在并行度上每个都执行一次
 */
public class MyRichMapFunction extends RichMapFunction<Integer, Integer> {
    // 默认生命周期方法, 初始化方法, 在每个并行度上只会被调用一次
    @Override
    public void open(Configuration parameters) throws Exception {
        System.out.println("open ... 执行一次");
    }

    // 默认生命周期方法, 最后一个方法, 做一些清理工作, 在每个并行度上只调用一次
    @Override
    public void close() throws Exception {
        System.out.println("close ... 执行一次");
    }

    @Override
    public Integer map(Integer value) throws Exception {
        System.out.println("map ... 一个元素执行一次");
        return value * value;
    }
}

