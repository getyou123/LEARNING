package org.example.flink_training.WaterMarkLearn;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.example.flink_training.pojo.WaterSensor;

/**
 * 自定义水印生成器
 * 模拟最大乱序程度的水印生成器
 */
public class MyWaterMarkGeneratorForWaterSersor implements WatermarkGenerator<WaterSensor> {
    // 定义最大的乱序程度
    private final long maxOutOfOrderness = 3500; // 3.5 秒

    // 定义当前最大的时间戳
    private long currentMaxTimestamp;

    /**
     * 时间来临时候更新currentMaxTimestamp，onEvent() 观察传入的事件数据
     *
     * @param event
     * @param eventTimestamp
     * @param output
     */
    @Override
    public void onEvent(WaterSensor event, long eventTimestamp, WatermarkOutput output) {
        currentMaxTimestamp = Math.max(currentMaxTimestamp, eventTimestamp);
    }

    /**
     * 框架调用onPeriodicEmit()时发出watermark
     * 生成watermark的时间间隔（每 n 毫秒）可以通过ExecutionConfig.setAutoWatermarkInterval(...) 指定
     * 每次都会调用生成器的onPeriodicEmit()方法，如果返回的watermark非空且值大于前一个watermark，则将发出新的watermark
     *
     * @param output
     */
    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        // 发出的 watermark = 当前最大时间戳 - 最大乱序时间
        output.emitWatermark(new Watermark(currentMaxTimestamp - maxOutOfOrderness - 1));
    }
}
