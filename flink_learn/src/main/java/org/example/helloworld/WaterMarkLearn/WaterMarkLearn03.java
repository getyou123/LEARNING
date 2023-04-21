package org.example.helloworld.WaterMarkLearn;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.entity.TaxiFare;

/**
 * 基于特定的事件来生成WaterMark
 */
public class WaterMarkLearn03 implements WatermarkGenerator<TaxiFare> {

    @Override
    public void onEvent(TaxiFare taxiFare, long l, WatermarkOutput watermarkOutput) {
        if (taxiFare.paymentType.equals("CASH")) {
            watermarkOutput.emitWatermark(new Watermark(taxiFare.getEventTimeMillis()));
        }
    }

    @Override
    public void onPeriodicEmit(WatermarkOutput watermarkOutput) {

    }
}
