package org.example.helloworld.WaterMarkLearn;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.helloworld.pojo.WaterSensor;

public class MyWaterMarkGeneratorForWaterSersor implements WatermarkGenerator<WaterSensor> {
    private final long maxOutOfOrderness = 3500; // 3.5 秒

    private long currentMaxTimestamp;

    @Override
    public void onEvent(WaterSensor event, long eventTimestamp, WatermarkOutput output) {
        currentMaxTimestamp = Math.max(currentMaxTimestamp, eventTimestamp);
    }

    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        // 发出的 watermark = 当前最大时间戳 - 最大乱序时间
        output.emitWatermark(new Watermark(currentMaxTimestamp - maxOutOfOrderness - 1));
    }
}
