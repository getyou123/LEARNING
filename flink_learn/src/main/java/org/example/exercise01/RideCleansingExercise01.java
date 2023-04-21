package org.example.exercise01;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.entity.TaxiRide;
import org.example.source.TaxiRideGenerator;
import org.example.utils.GeoUtils;

/**
 * 简单的进行数据filter
 */
public class RideCleansingExercise01 {
    public static void main(String[] args) {
        // 获取env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // add source
        DataStream<TaxiRide> rides = env.addSource(new TaxiRideGenerator());

        rides.filter(new FilterFunction<TaxiRide>() {
                    @Override
                    public boolean filter(TaxiRide taxiRide) throws Exception {
                        return GeoUtils.isInNYC(taxiRide.startLon, taxiRide.startLat) && GeoUtils.isInNYC(taxiRide.endLon, taxiRide.endLat);
                    }
                })
                .filter(taxiRide -> GeoUtils.isInNYC(taxiRide.startLon, taxiRide.startLat) && GeoUtils.isInNYC(taxiRide.endLon, taxiRide.endLat));
    }
}
