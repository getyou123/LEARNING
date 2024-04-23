package org.example;

import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.example.streaming_with_flink.chapter8.ResettableCountSource;
import org.junit.ClassRule;
import org.junit.Test;

public class ResettableCountSourceTest {
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


    /**
     * 测试repeatable的数据源
     */
    @Test
    public void ResettableCountSourceTest001() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new ResettableCountSource()).print("测试数据输出：");
        env.execute();

    }
}
