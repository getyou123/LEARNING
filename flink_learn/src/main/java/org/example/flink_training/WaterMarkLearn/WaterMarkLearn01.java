package org.example.flink_training.WaterMarkLearn;


import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.flink_training.entity.TaxiFare;

/**
 * 自定义Watermark
 * 实现接口，重写方法
 * 可以基于事件或者周期性的生成 watermark。
 * 下面演示的是基于事件时间的最大乱序程度的 maxOutOfOrderness 底层的基本原理
 */
public class WaterMarkLearn01 implements WatermarkGenerator<TaxiFare> {

    private final long maxOutOfOrderness = 3500; // 3.5 秒

    private long currentMaxTimestamp;

    /**
     * 每来一条事件数据调用一次，可以检查或者记录事件的时间戳，或者也可以基于事件数据本身去生成 watermark。
     * 可以看下 WatermarkStrategy中的 forBoundedOutOfOrderness 的源码
     * 这里他没有       output.emitWatermark(new Watermark(currentMaxTimestamp));
     * 应该是考虑到了效率，走的定期的 onPeriodicEmit这个函数
     */
    @Override
    public void onEvent(TaxiFare taxiFare, long eventTimestamp, WatermarkOutput output) {
        currentMaxTimestamp = Math.max(currentMaxTimestamp, eventTimestamp);
    }

    /**
     * 周期性的调用，也许会生成新的 watermark，也许不会。
     *
     * <p>调用此方法生成 watermark 的间隔时间由 @link ExecutionConfig#getAutoWatermarkInterval() 决定。
     * 定期的产出 当前最大的时间时间戳- maxOutOfOrderness - 1 作为waterMark
     */
    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        // 发出的 watermark = 当前最大时间戳 - 最大乱序时间
        output.emitWatermark(new Watermark(currentMaxTimestamp - maxOutOfOrderness - 1));
    }
}
