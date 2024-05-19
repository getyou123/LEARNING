package org.example.streaming_with_flink.chapter6;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.example.streaming_with_flink.util.SensorReading;
import org.example.streaming_with_flink.util.SensorSource;
import org.example.streaming_with_flink.util.SensorTimeAssigner;

/**
 * 展示使用侧数据输出流量
 */
public class SideOutputs {

    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Checkpoint every 10 seconds
        env.getCheckpointConfig().setCheckpointInterval(10 * 1000);

        // Use event time for the application
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // Configure watermark interval
        env.getConfig().setAutoWatermarkInterval(1000L);

        // Ingest sensor stream
        DataStream<SensorReading> readings = env
                // SensorSource generates random temperature readings
                .addSource(new SensorSource())
                // Assign timestamps and watermarks required for event time
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        DataStream<SensorReading> monitoredReadings = readings
                // Monitor stream for readings with freezing temperatures
                .process(new FreezingMonitor());

        // Retrieve and print the freezing alarms
        ((SingleOutputStreamOperator<SensorReading>) monitoredReadings)
                .getSideOutput(new OutputTag<String>("freezing-alarms"))
                .print();

        // Print the main output
        readings.print();

        env.execute();
    }
}

/**
 * Emits freezing alarms to a side output for readings with a temperature below 32F.
 */
class FreezingMonitor extends ProcessFunction<SensorReading, SensorReading> {

    // Define a side output tag
    private final OutputTag<String> freezingAlarmOutput = new OutputTag<>("freezing-alarms");

    @Override
    public void processElement(SensorReading r, Context ctx, Collector<SensorReading> out) throws Exception {
        // Emit freezing alarm if temperature is below 32F
        if (r.temperature < 32.0) {
            ctx.output(freezingAlarmOutput, "Freezing Alarm for " + r.id);
        }
        // Forward all readings to the regular output
        out.collect(r);
    }
}