package org.example.flink_training.exercise01;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.example.flink_training.entity.TaxiRide;
import org.example.flink_training.source.TaxiRideGenerator;
import org.example.flink_training.utils.GeoUtils;

/**
 * 认为他是个job，并且source和sink是非固定的
 */
public class RideCleansingExercise {
    // 最好是持有这个env中的source和sink因为这个很好的切换
    private final SourceFunction<TaxiRide> source;

    private final SinkFunction<TaxiRide> sink;

    /**
     * Creates a job using the source and sink provided.
     */
    public RideCleansingExercise(SourceFunction<TaxiRide> source, SinkFunction<TaxiRide> sink) {
        this.source = source;
        this.sink = sink;
    }

    /**
     * 既然都包含source 和 sink，那就也包含execute方法
     *
     * @return
     * @throws Exception
     */
    public JobExecutionResult execute() throws Exception {

        // set up streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // set up the pipeline
        env.addSource(source).filter(new NYCFilter()).addSink(sink);

        // run the pipeline and return the result
        return env.execute("Taxi Ride Cleansing");
    }

    /**
     * 这里实现过滤
     */
    public static class NYCFilter implements FilterFunction<TaxiRide> {
        @Override
        public boolean filter(TaxiRide taxiRide) throws Exception {
            return GeoUtils.isInNYC(taxiRide.startLon, taxiRide.startLat) && GeoUtils.isInNYC(taxiRide.endLon, taxiRide.endLat);
        }
    }


    public static void main(String[] args) throws Exception {
        // 这里从测试对接线上就很舒服
        RideCleansingExercise job = new RideCleansingExercise(new TaxiRideGenerator(), new PrintSinkFunction<>());
        job.execute();
    }
}
