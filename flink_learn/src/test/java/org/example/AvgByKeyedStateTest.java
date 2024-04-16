package org.example;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.example.flink_training.StateLearn.CountWindowAverage;
import org.junit.ClassRule;
import org.junit.Test;

public class AvgByKeyedStateTest {
    private static final int PARALLELISM = 2;

    /**
     * This isn't necessary, but speeds up the tests.
     */
    @ClassRule
    public static MiniClusterWithClientResource flinkCluster =
            new MiniClusterWithClientResource(
                    new MiniClusterResourceConfiguration.Builder()
                            .setNumberSlotsPerTaskManager(PARALLELISM)
                            .setNumberTaskManagers(1)
                            .build());


    @Test
    public void AvgByKeyedStateTest1 () throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // this can be used in a streaming program like this (assuming we have a StreamExecutionEnvironment env)
        env.fromElements(Tuple2.of(1L, 3L),
                        Tuple2.of(3L, 2L),
                        Tuple2.of(3L, 4L),
                        Tuple2.of(1L, 2L)
                        )
                .keyBy(value -> value.f0)
                .flatMap(new CountWindowAverage())
                .print();

        // 逻辑......
        env.execute(AvgByKeyedStateTest.class.getSimpleName() + "JavaJob");
    }
}
