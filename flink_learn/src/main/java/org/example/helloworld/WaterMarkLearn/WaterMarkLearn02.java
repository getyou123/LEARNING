package org.example.helloworld.WaterMarkLearn;


import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.entity.TaxiFare;

/**
 * 这里演示根据当前的处理时间延迟 5s产生watermark
 */
public class WaterMarkLearn02 implements WatermarkGenerator<TaxiFare> {
    private final long maxTimeLag = 5000; // 5 秒


    /**
     * 无需处理
     */
    @Override
    public void onEvent(TaxiFare taxiFare, long l, WatermarkOutput watermarkOutput) { // 事件及其时间戳

    }

    /**
     * 周期性的插入 当前时间的 5s 时延作为 wm
     * @param output
     */

    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        output.emitWatermark(new Watermark(System.currentTimeMillis() - maxTimeLag));
    }


}
