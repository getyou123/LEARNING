package org.example.streaming_with_flink.chapter7;

import org.apache.flink.api.common.state.*;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.example.streaming_with_flink.util.SensorReading;
import org.example.streaming_with_flink.util.SensorSource;
import org.example.streaming_with_flink.util.SensorTimeAssigner;

public class BroadcastStateFunction {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // checkpoint every 10 seconds
        env.getCheckpointConfig().setCheckpointInterval(10 * 1000);

        // use event time for the application
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // configure watermark interval
        env.getConfig().setAutoWatermarkInterval(1000L);

        // ingest sensor stream
        DataStream<SensorReading> sensorData = env
                // SensorSource generates random temperature readings
                .addSource(new SensorSource())
                // assign timestamps and watermarks which are required for event time
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        // define a stream of thresholds
        DataStream<ThresholdUpdate> thresholds = env.fromElements(
                new ThresholdUpdate("sensor_1", 5.0d),
                new ThresholdUpdate("sensor_2", 0.9d),
                new ThresholdUpdate("sensor_3", 0.5d),
                new ThresholdUpdate("sensor_1", 1.2d),  // update threshold for sensor_1
                new ThresholdUpdate("sensor_3", 0.0d)   // disable threshold for sensor_3
        );

        KeyedStream<SensorReading, String> keyedSensorData = sensorData.keyBy(SensorReading -> SensorReading.id);
        // 广播状态的描述符
        MapStateDescriptor<String, Double> broadcastStateDescriptor = new MapStateDescriptor<>("thresholds", String.class, Double.class);

        // 创建广播流
        BroadcastStream<ThresholdUpdate> broadcastThresholds = thresholds.broadcast(broadcastStateDescriptor);

        // 链接两种流
        DataStream<Tuple3<String, Double, Double>> alerts = keyedSensorData
                .connect(broadcastThresholds)
                .process(new UpdatableTemperatureAlertFunction());

        // print result stream to standard out
        alerts.print();

        // execute application
        env.execute("Generate Temperature Alerts");
    }

    public static class ThresholdUpdate {
        public final String id;
        public final double threshold;

        public ThresholdUpdate(String id, double threshold) {
            this.id = id;
            this.threshold = threshold;
        }
    }

    /**
     * The function emits an alert if the temperature measurement of a sensor changed by more than
     * a threshold compared to the last reading. The thresholds are configured per sensor by a separate stream.
     */
    private static class UpdatableTemperatureAlertFunction
            extends KeyedBroadcastProcessFunction<String, SensorReading, ThresholdUpdate, Tuple3<String, Double, Double>> {

        private static final long serialVersionUID = 1L;

        private transient ValueState<Double> lastTempState;

        // 广播状态的描述符
        private transient MapStateDescriptor<String, Double> thresholdStateDescriptor;

        @Override
        public void open(Configuration parameters) throws Exception {
            // create state descriptor
            ValueStateDescriptor<Double> lastTempDescriptor = new ValueStateDescriptor<>("lastTemp", Double.class);
            // obtain the state handle
            lastTempState = getRuntimeContext().getState(lastTempDescriptor);

            thresholdStateDescriptor = new MapStateDescriptor<>("thresholds", String.class, Double.class);
        }

        /**
         * 更新算子实例中的广播状态
         */
        @Override
        public void processBroadcastElement(ThresholdUpdate update, Context ctx, Collector<Tuple3<String, Double, Double>> out) throws Exception {
            BroadcastState<String, Double> thresholds = ctx.getBroadcastState(thresholdStateDescriptor);

            if (update.threshold != 0.0d) {
                // configure a new threshold for the sensor
                thresholds.put(update.id, update.threshold);
            } else {
                // remove threshold for the sensor
                thresholds.remove(update.id);
            }
        }

        /**
         * 获取广播状态然后判定是不是超过了范围
         * 然后报警
         */
        @Override
        public void processElement(SensorReading reading, ReadOnlyContext ctx, Collector<Tuple3<String, Double, Double>> out) throws Exception {
            // get read-only broadcast state
            ReadOnlyBroadcastState<String, Double> thresholds = ctx.getBroadcastState(thresholdStateDescriptor);
            // check if we have a threshold
            if (thresholds.contains(reading.id)) {
                // get threshold for sensor
                double sensorThreshold = thresholds.get(reading.id);

                // fetch the last temperature from state
                double lastTemp = lastTempState.value();
                // check if we need to emit an alert
                double tempDiff = Math.abs(reading.temperature - lastTemp);
                if (tempDiff > sensorThreshold) {
                    // temperature increased by more than the threshold
                    out.collect(new Tuple3<>(reading.id, reading.temperature, tempDiff));
                }
            }

            // update lastTemp state
            lastTempState.update(reading.temperature);
        }
    }
}