package org.example.streaming_with_flink.chapter8;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.example.streaming_with_flink.util.SensorReading;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A resettable Flink SourceFunction to generate SensorReadings with random temperature values.
 * <p>
 * Each parallel instance of the source simulates 10 sensors which emit one sensor
 * reading every 100 ms.
 * <p>
 * The sink is integrated with Flink's checkpointing mechanism and can be reset to reproduce
 * previously emitted records.
 */
public class ResettableSensorSource extends RichParallelSourceFunction<SensorReading>
        implements CheckpointedFunction {

    private boolean running;

    private SensorReading[] readings;

    private transient ListState<SensorReading> sensorsState;

    @Override
    public void run(SourceContext<SensorReading> ctx) throws Exception {
        // initialize random number generator
        Random rand = new Random();

        // emit data until being canceled
        while (running) {
            // take a lock to ensure we don't emit while taking a checkpoint
            synchronized (ctx.getCheckpointLock()) {
                // emit readings for all sensors
                for (int i = 0; i < readings.length; i++) {
                    // get reading
                    SensorReading reading = readings[i];

                    // update timestamp and temperature
                    long newTime = reading.timestamp + 100;
                    // set seed for deterministic temperature generation
                    rand.setSeed(newTime ^ (long) reading.temperature);
                    double newTemp = reading.temperature + (rand.nextGaussian() * 0.5);
                    SensorReading newReading = new SensorReading(reading.id, newTime, newTemp);

                    // store new reading and emit it
                    readings[i] = newReading;
                    ctx.collect(newReading);
                }
            }

            // wait for 100 ms
            Thread.sleep(100);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        // define state of sink as union list operator state
        this.sensorsState = context.getOperatorStateStore().getUnionListState(
                new ListStateDescriptor<>("sensorsState", SensorReading.class));

        // get iterator over state
        Iterator<SensorReading> sensorsStateIt = sensorsState.get().iterator();

        if (!sensorsStateIt.hasNext()) {
            // state is empty, this is the first run
            // create initial sensor data
            Random rand = new Random();
            int numTasks = getRuntimeContext().getNumberOfParallelSubtasks();
            int thisTask = getRuntimeContext().getIndexOfThisSubtask();
            long curTime = Calendar.getInstance().getTimeInMillis();

            // initialize sensor ids and temperatures
            List<SensorReading> readings = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                int idx = thisTask + i * numTasks;
                String sensorId = "sensor_" + idx;
                double temp = 65 + rand.nextGaussian() * 20;
                readings.add(new SensorReading(sensorId, curTime, temp));
            }
            this.readings = readings.toArray(new SensorReading[0]);
        } else {
            // select the sensors to handle in this task
            int numTasks = getRuntimeContext().getNumberOfParallelSubtasks();
            int thisTask = getRuntimeContext().getIndexOfThisSubtask();

            List<SensorReading> allReadings = new ArrayList<>();
            while (sensorsStateIt.hasNext()) {
                allReadings.add(sensorsStateIt.next());
            }
            this.readings = allReadings.stream()
                    .filter(r -> allReadings.indexOf(r) % numTasks == thisTask)
                    .toArray(SensorReading[]::new);
        }

        running = true;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        // replace sensor state by current readings
        sensorsState.update(Arrays.asList(readings));
    }
}