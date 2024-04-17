package org.example.flink_training.exercise02;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.example.flink_training.entity.RideAndFare;
import org.example.flink_training.entity.TaxiFare;
import org.example.flink_training.entity.TaxiRide;


public class RidesAndFaresExercise {
    private final SourceFunction<TaxiRide> rideSource;
    private final SourceFunction<TaxiFare> fareSource;
    private final SinkFunction<RideAndFare> sink;

    /**
     * 构造函数
     *
     * @param rideSource
     * @param fareSource
     * @param sink
     */
    public RidesAndFaresExercise(
            SourceFunction<TaxiRide> rideSource,
            SourceFunction<TaxiFare> fareSource,
            SinkFunction<RideAndFare> sink) {

        this.rideSource = rideSource;
        this.fareSource = fareSource;
        this.sink = sink;
    }

    public JobExecutionResult execute() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // A stream of taxi ride START events, keyed by rideId.
        DataStream<TaxiRide> rides =
                env.addSource(rideSource)
                        .filter(ride -> ride.isStart)
                        .keyBy(ride -> ride.rideId);

        // A stream of taxi fare events, also keyed by rideId.
        DataStream<TaxiFare> fares = env.addSource(fareSource)
                .keyBy(fare -> fare.rideId);

         rides.connect(fares).flatMap(new EnrichmentFunction()).addSink(sink);

        return env.execute("Join Rides with Fares");
    }

    /**
     * 实现两个
     */
    public static class EnrichmentFunction extends RichCoFlatMapFunction<TaxiRide, TaxiFare, RideAndFare> {

        // 这里是每个key存储一个状态，因为起初是认为一个匹配一个的，所以这里使用的ValueState 就可了
        private ValueState<TaxiRide> rideState;

        // 这里是每个key存储一个状态
        private ValueState<TaxiFare> fareState;

        // 开始时候都是null
        @Override
        public void open(Configuration config) throws Exception {
            rideState =
                    getRuntimeContext()
                            .getState(new ValueStateDescriptor<>("saved ride", TaxiRide.class));
            fareState =
                    getRuntimeContext()
                            .getState(new ValueStateDescriptor<>("saved fare", TaxiFare.class));
        }


        // 处理TaxiRide，第一次走的是else分支
        @Override
        public void flatMap1(TaxiRide ride, Collector<RideAndFare> out) throws Exception {
            TaxiFare fare = fareState.value();
            if (fare != null) {
                fareState.clear();
                out.collect(new RideAndFare(ride, fare));
            } else {
                rideState.update(ride);
            }
        }

        @Override
        public void flatMap2(TaxiFare fare, Collector<RideAndFare> out) throws Exception {
            TaxiRide ride = rideState.value();
            if (ride != null) {
                rideState.clear();
                out.collect(new RideAndFare(ride, fare));
            } else {
                fareState.update(fare);
            }
        }
    }
}

