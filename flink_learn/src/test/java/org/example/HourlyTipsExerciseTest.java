package org.example;


import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;

import org.apache.flink.test.util.MiniClusterWithClientResource;

import org.example.entity.TaxiFare;


import org.example.exercise03.HourlyTipsExercise;
import org.example.tool.ParallelTestSource;
import org.example.tool.TestSink;
import org.junit.ClassRule;
import org.junit.Test;


import static org.example.SomeStaticTool.t;
import static org.example.SomeStaticTool.testFare;
import static org.assertj.core.api.Assertions.assertThat;


public class HourlyTipsExerciseTest {
    private static final int PARALLELISM = 1;

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
    public void HourlyTipsExerciseTest01 () throws Exception {

        // 构造的输入数据
        TaxiFare oneFor1In1 = testFare(1, t(0), 1.0F);
        TaxiFare fiveFor1In1 = testFare(1, t(15), 5.0F);
        TaxiFare tenFor1In2 = testFare(1, t(90), 10.0F);
        TaxiFare twentyFor2In2 = testFare(2, t(90), 20.0F);
        TaxiFare zeroFor3In2 = testFare(3, t(70), 0.0F);
        TaxiFare zeroFor4In2 = testFare(4, t(70), 0.0F);
        TaxiFare oneFor4In2 = testFare(4, t(80), 1.0F);
        TaxiFare tenFor5In2 = testFare(5, t(100), 10.0F);

        ParallelTestSource<TaxiFare> source =
                new ParallelTestSource<>(
                        oneFor1In1,
                        fiveFor1In1,
                        tenFor1In2,
                        twentyFor2In2,
                        zeroFor3In2,
                        zeroFor4In2,
                        oneFor4In2,
                        tenFor5In2);


        // 预计的结果
        Tuple3<Long, Long, Float> hour1 = Tuple3.of(t(60).toEpochMilli(), 1L, 6.0F);
        Tuple3<Long, Long, Float> hour2 = Tuple3.of(t(120).toEpochMilli(), 2L, 20.0F);

        TestSink<Tuple3<Long, Long, Float>> sink = new TestSink<>();

        JobExecutionResult jobResult = new HourlyTipsExercise(source, sink).execute();

        assertThat(sink.getResults(jobResult)).containsExactlyInAnyOrder(hour1, hour2);


    }
    @Test
    public void HourlyTipsExerciseTest02 () throws Exception {

        TaxiFare oneFor1In1 = testFare(1, t(0), 1.0F);
        TaxiFare fiveFor1In1 = testFare(1, t(15), 5.0F);
        TaxiFare tenFor1In2 = testFare(1, t(90), 10.0F);
        TaxiFare twentyFor2In2 = testFare(2, t(90), 20.0F);
        TaxiFare zeroFor3In2 = testFare(3, t(70), 0.0F);
        TaxiFare zeroFor4In2 = testFare(4, t(70), 0.0F);
        TaxiFare oneFor4In2 = testFare(4, t(80), 1.0F);
        TaxiFare tenFor5In2 = testFare(5, t(100), 10.0F);


        ParallelTestSource<TaxiFare> source =
                new ParallelTestSource<>(
                        oneFor1In1,
                        fiveFor1In1,
                        tenFor1In2,
                        twentyFor2In2,
                        zeroFor3In2,
                        zeroFor4In2,
                        oneFor4In2,
                        tenFor5In2);

        TestSink<Tuple3<Long, Long, Float>> sink = new TestSink<>();

        JobExecutionResult jobResult = new HourlyTipsExercise(source, sink).execute();

        System.out.println(sink.getResults(jobResult));


    }
}
