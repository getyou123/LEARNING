package org.example.streaming_with_flink.chapter6;


import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.streaming_with_flink.util.SensorReading;
import org.example.streaming_with_flink.util.SensorSource;

public class ProcessFunctionTimers {

    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Use processing time for the application
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        // Ingest sensor stream
        DataStream<SensorReading> readings = env
                // SensorSource generates random temperature readings
                .addSource(new SensorSource());

        DataStream<String> warnings = readings
                // Key by sensor id
                .keyBy(SensorReading -> SensorReading.id)
                // Apply ProcessFunction to monitor temperatures
                .process(new TempIncreaseAlertFunction());

        warnings.print();

        env.execute("Monitor sensor temperatures.");
    }
}

/**
 * Emits a warning if the temperature of a sensor monotonically increases for 1 second (in processing time).
 */
class TempIncreaseAlertFunction extends KeyedProcessFunction<String, SensorReading, String> {

    // Hold the temperature of the last sensor reading
    private ValueState<Double> lastTemp;
    // Hold the timestamp of the currently active timer
    private ValueState<Long> currentTimer;

    @Override
    public void open(org.apache.flink.configuration.Configuration parameters) throws Exception {
        lastTemp = getRuntimeContext().getState(
                new ValueStateDescriptor<>("lastTemp", Double.class)
        );

        currentTimer = getRuntimeContext().getState(
                new ValueStateDescriptor<>("timer", Long.class)
        );
    }

    @Override
    public void processElement(SensorReading r, Context ctx, Collector<String> out) throws Exception {
        // Get the previous temperature
        double prevTemp = lastTemp.value() != null ? lastTemp.value() : 0.0;

        // Update the last temperature
        lastTemp.update(r.temperature);

        long curTimerTimestamp = currentTimer.value() != null ? currentTimer.value() : 0L;

        if (prevTemp == 0.0) {
            // First sensor reading for this key.
            // We cannot compare it with a previous value.
        } else if (r.temperature < prevTemp) {
            // Temperature decreased. Delete the current timer.
            ctx.timerService().deleteProcessingTimeTimer(curTimerTimestamp);
            currentTimer.clear();
        } else if (r.temperature > prevTemp && curTimerTimestamp == 0L) {
            // Temperature increased and we have not set a timer yet.
            // Set a timer for now + 1 second
            long timerTs = ctx.timerService().currentProcessingTime() + 1000;
            ctx.timerService().registerProcessingTimeTimer(timerTs);
            // Remember the current timer
            currentTimer.update(timerTs);
        }
    }

    @Override
    public void onTimer(long ts, OnTimerContext ctx, Collector<String> out) throws Exception {
        out.collect("Temperature of sensor '" + ctx.getCurrentKey() +
                "' monotonically increased for 1 second.");
        // Reset the current timer
        currentTimer.clear();
    }
}