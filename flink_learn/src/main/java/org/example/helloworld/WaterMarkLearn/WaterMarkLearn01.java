package org.example.helloworld.WaterMarkLearn;


import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.entity.TaxiFare;

/**
 * 自定义Watermark
 * 实现接口，重写方法
 * 可以基于事件或者周期性的生成 watermark。
 * 下面演示的是基于事件时间的最大乱序程度的 maxOutOfOrderness 底层的基本与阿里
 */
public class WaterMarkLearn01 implements WatermarkGenerator<TaxiFare> {

    private final long maxOutOfOrderness = 3500; // 3.5 秒

    private long currentMaxTimestamp;

    /**
     * 每来一条事件数据调用一次，可以检查或者记录事件的时间戳，或者也可以基于事件数据本身去生成 watermark。
     */
    @Override
    public void onEvent(TaxiFare taxiFare, long eventTimestamp, WatermarkOutput watermarkOutput) {
        currentMaxTimestamp = Math.max(currentMaxTimestamp, eventTimestamp);
    }

    /**
     * 周期性的调用，也许会生成新的 watermark，也许不会。
     *
     * <p>调用此方法生成 watermark 的间隔时间由 @link ExecutionConfig#getAutoWatermarkInterval() 决定。
     */
    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        // 发出的 watermark = 当前最大时间戳 - 最大乱序时间
        output.emitWatermark(new Watermark(currentMaxTimestamp - maxOutOfOrderness - 1));
    }
}
