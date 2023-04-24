
package org.example.helloworld;


import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.streaming.api.windowing.time.Time;


public class DataStreamJob {
    private final SourceFunction<String> source;
    private final SinkFunction<WordCount> sink;

    public DataStreamJob( SourceFunction<String> source, SinkFunction<WordCount> sink) {

        this.source = source;
        this.sink = sink;
    }

    private static final Logger log = LoggerFactory.getLogger(DataStreamJob.class);

    public JobExecutionResult execute() throws Exception {
        log.info("开始java world count.......");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = env.addSource(source)
                .assignTimestampsAndWatermarks( // 设置时间戳和WatermarkStrategy
                        WatermarkStrategy.<String>forMonotonousTimestamps()
                                .withTimestampAssigner(
                                        (event, t) -> System.currentTimeMillis()
                                )
                );

        // 单词统计
        DataStream<WordCount> wordCounts =
                stringSingleOutputStreamOperator.flatMap(new FlatMapFunction<String, WordCount>() {
                            @Override
                            public void flatMap(String line, Collector<WordCount> collector) throws Exception {
                                for (String word : line.split(" ")) {
                                    collector.collect(new WordCount(word, 1));
                                }
                            }
                        })
                        .keyBy(new KeySelector<WordCount, Object>() {
                            @Override
                            public Object getKey(WordCount wordCount) throws Exception {
                                return wordCount.word;
                            }
                        })
//                        .timeWindow(Time.seconds(5))
                        .sum("count");


        // 打印结果
        wordCounts.addSink(sink);

        // 逻辑......
        return env.execute(DataStreamJob.class.getSimpleName() + "JavaJob");
    }
}
