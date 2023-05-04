package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.helloworld.StateLearn.MyCountMapper;

import org.junit.Test;


public class OperatorStateLearn {

    /**
     * 测试每个算子的单独计算自己
     * 已经处理的数据count
     */
    @Test
    public void OperatorStateLearn001() {
        try {

            StreamExecutionEnvironment env = StreamExecutionEnvironment
                    .getExecutionEnvironment()
                    .setParallelism(3);

            env
                    .socketTextStream("localhost", 9999)
                    .map(new MyCountMapper())
                    .print();

            env.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
