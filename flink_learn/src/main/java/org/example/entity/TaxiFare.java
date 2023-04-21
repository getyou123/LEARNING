package org.example.entity;

import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.example.utils.DataGenerator;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 车费事件结构
 *
 */

public class TaxiFare implements Serializable {

    /** Creates a TaxiFare with now as the start time. */
    public TaxiFare() {
        this.startTime = Instant.now();
    }

    /** Invents a TaxiFare. */
    public TaxiFare(long rideId) {
        DataGenerator g = new DataGenerator(rideId);

        this.rideId = rideId;
        this.taxiId = g.taxiId();
        this.driverId = g.driverId();
        this.startTime = g.startTime();
        this.paymentType = g.paymentType();
        this.tip = g.tip();
        this.tolls = g.tolls();
        this.totalFare = g.totalFare();
    }

    /** Creates a TaxiFare with the given parameters. */
    public TaxiFare(
            long rideId,
            long taxiId,
            long driverId,
            Instant startTime,
            String paymentType,
            float tip,
            float tolls,
            float totalFare) {
        this.rideId = rideId;
        this.taxiId = taxiId;
        this.driverId = driverId;
        this.startTime = startTime;
        this.paymentType = paymentType;
        this.tip = tip;
        this.tolls = tolls;
        this.totalFare = totalFare;
    }

    public long rideId; // 每次车程的唯一id
    public long taxiId; // 每一辆出租车的唯一id
    public long driverId; // 每一位司机的唯一id
    public Instant startTime;  // 车程开始时间
    public String paymentType; // 现金(CASH)或刷卡(CARD)
    public float tip;  // 小费
    public float tolls;  // 过路费
    public float totalFare; // 总计车费

    @Override
    public String toString() {

        return rideId
                + ","
                + taxiId
                + ","
                + driverId
                + ","
                + startTime.toString()
                + ","
                + paymentType
                + ","
                + tip
                + ","
                + tolls
                + ","
                + totalFare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxiFare taxiFare = (TaxiFare) o;
        return rideId == taxiFare.rideId
                && taxiId == taxiFare.taxiId
                && driverId == taxiFare.driverId
                && Float.compare(taxiFare.tip, tip) == 0
                && Float.compare(taxiFare.tolls, tolls) == 0
                && Float.compare(taxiFare.totalFare, totalFare) == 0
                && Objects.equals(startTime, taxiFare.startTime)
                && Objects.equals(paymentType, taxiFare.paymentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                rideId, taxiId, driverId, startTime, paymentType, tip, tolls, totalFare);
    }

    /** Gets the fare's start time. */
    public long getEventTimeMillis() {
        return startTime.toEpochMilli();
    }

    /** Creates a StreamRecord, using the fare and its timestamp. Used in tests. */
    @VisibleForTesting
    public StreamRecord<TaxiFare> asStreamRecord() {
        return new StreamRecord<>(this, this.getEventTimeMillis());
    }
}