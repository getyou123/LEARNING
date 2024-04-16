package org.example;

import org.example.flink_training.entity.TaxiFare;
import org.example.flink_training.entity.TaxiRide;
import org.example.flink_training.utils.DataGenerator;

import java.time.Duration;
import java.time.Instant;

public  class SomeStaticTool {
    public static TaxiFare testFare(long driverId, Instant startTime, float tip) {
        return new TaxiFare(0, 0, driverId, startTime, "", tip, 0F, 0F);
    }


    public static TaxiRide testRide(float startLon, float startLat, float endLon, float endLat) {
        return new TaxiRide(
                1L, true, Instant.EPOCH, startLon, startLat, endLon, endLat, (short) 1, 0, 0);
    }

    public static TaxiRide startRide(long rideId, Instant startTime) {
        return testRide(rideId, true, startTime);
    }

    private static TaxiRide testRide(long rideId, Boolean isStart, Instant eventTime) {

        return new TaxiRide(
                rideId,
                isStart,
                eventTime,
                -73.9947F,
                40.750626F,
                -73.9947F,
                40.750626F,
                (short) 1,
                0,
                0);
    }


    public static TaxiRide testRide(long rideId) {
        return new TaxiRide(rideId, true, Instant.EPOCH, 0F, 0F, 0F, 0F, (short) 1, 0, rideId);
    }

    public static TaxiFare testFare(long rideId) {
        return new TaxiFare(rideId, 0, rideId, Instant.EPOCH, "", 0F, 0F, 0F);
    }

    public static Instant t(int minutes) {
        return DataGenerator.BEGINNING.plus(Duration.ofMinutes(minutes));
    }

}
