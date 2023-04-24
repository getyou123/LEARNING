package org.example;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.example.helloworld.DataStreamJob;
import org.example.helloworld.WordCount;
import org.example.tool.ParallelTestSource;
import org.example.tool.TestSink;
import org.junit.ClassRule;
import org.junit.Test;

public class HelloWorldTest {
//    private static final int PARALLELISM = 2;
//
//    /**
//     * This isn't necessary, but speeds up the tests.
//     */
//    @ClassRule
//    public static MiniClusterWithClientResource flinkCluster =
//            new MiniClusterWithClientResource(
//                    new MiniClusterResourceConfiguration.Builder()
//                            .setNumberSlotsPerTaskManager(PARALLELISM)
//                            .setNumberTaskManagers(1)
//                            .build());

    @Test
    public void HelloWorldTest01() throws Exception {

        ParallelTestSource<String> stringParallelTestSource = new ParallelTestSource<>(
                "dsaf dfsjaif",
                "fdsaf"

        );

        TestSink<WordCount> wordCountTestSink = new TestSink<>();

        DataStreamJob dataStreamJob = new DataStreamJob(stringParallelTestSource, wordCountTestSink);

        JobExecutionResult execute = dataStreamJob.execute();

        System.out.println(wordCountTestSink.getResults(execute));
    }
}
