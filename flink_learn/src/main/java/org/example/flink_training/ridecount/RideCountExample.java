package org.example.flink_training.ridecount;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.flink_training.entity.TaxiRide;
import org.example.flink_training.source.TaxiRideGenerator;


/**
 * 可以看做是这次训练的一个helle world
 * 计算每个driverID的count
 */
public class RideCountExample {

    /**
     * Main method.
     *
     * @throws Exception which occurs during job execution.
     */
    public static void main(String[] args) throws Exception {

        // set up streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // start the data generator
        DataStream<TaxiRide> rides = env.addSource(new TaxiRideGenerator());

        // map each ride to a tuple of (driverId, 1)
        DataStream<Tuple2<Long, Long>> tuples =
                rides.map(
                        new MapFunction<TaxiRide, Tuple2<Long, Long>>() {
                            @Override
                            public Tuple2<Long, Long> map(TaxiRide ride) {
                                return Tuple2.of(ride.driverId, 1L);
                            }
                        });

        // partition the stream by the driverId
        KeyedStream<Tuple2<Long, Long>, Long> keyedByDriverId = tuples.keyBy(t -> t.f0);

        // count the rides for each driver
        DataStream<Tuple2<Long, Long>> rideCounts = keyedByDriverId.sum(1);

        // we could, in fact, print out any or all of these streams
        rideCounts.print();

        // run the cleansing pipeline
        env.execute("Ride Count");
    }
}