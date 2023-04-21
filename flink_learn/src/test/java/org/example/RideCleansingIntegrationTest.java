package org.example;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;


import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.example.entity.TaxiRide;
import org.example.exercise01.RideCleansingExercise;
import org.example.tool.ParallelTestSource;
import org.example.tool.TestSink;
import org.junit.ClassRule;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.SomeStaticTool.testRide;

import java.time.Instant;

public class RideCleansingIntegrationTest {

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
    public void testAMixtureOfLocations() throws Exception {

        // 构造几个测试数据
        TaxiRide toThePole = testRide(-73.9947F, 40.750626F, 0, 90);
        TaxiRide fromThePole = testRide(0, 90, -73.9947F, 40.750626F);
        TaxiRide atPennStation = testRide(-73.9947F, 40.750626F, -73.9947F, 40.750626F);
        TaxiRide atNorthPole = testRide(0, 90, 0, 90);

        // 这里构造了可以发送任何数据一个source，直接构造数据然后加入就可以获取到source，然后把要测试的数据放在了ParallelTestSource中
        ParallelTestSource<TaxiRide> source = new ParallelTestSource<>(toThePole, fromThePole, atPennStation, atNorthPole);

        // 这里构造了一个TestSink 用来作为数据的接收
        TestSink<TaxiRide> sink = new TestSink<>();

        // 构造图并进行 执行

        JobExecutionResult jobResult = new RideCleansingExercise(source, sink).execute();

        // assertThat 结果
        assertThat(sink.getResults(jobResult)).containsExactly(atPennStation);
    }

}

