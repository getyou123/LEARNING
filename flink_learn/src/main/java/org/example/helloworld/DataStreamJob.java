
package org.example.helloworld;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.streaming.api.windowing.time.Time;


public class DataStreamJob {
    private static final Logger log = LoggerFactory.getLogger(DataStreamJob.class);

    public static void main(String[] args) throws Exception {
        log.info("开始java world count.......");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> stream = env.socketTextStream("localhost", 7777);
        // 单词统计
        DataStream<WordCount> wordCounts =
                stream.flatMap(new FlatMapFunction<String, WordCount>() {
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
                        .timeWindow(Time.seconds(5))
                        .sum("count");

        // 打印结果
        wordCounts.print();
        // 逻辑......
        env.execute(DataStreamJob.class.getSimpleName() + "JavaJob");

    }
}
