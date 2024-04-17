package org.example.flink_training.StateLearn;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

/**
 * 算子的状态
 */

public class MyCountMapper implements MapFunction<String, Long>, CheckpointedFunction {
    private Long count = 0L;
    private ListState<Long> state;

    @Override
    public Long map(String value) throws Exception {
        count++;
        return count;
    }

    // 初始化时会调用这个方法，向本地状态中填充数据. 每个子任务调用一次
    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        System.out.println("initializeState...");
        state = context
                .getOperatorStateStore()
                .getListState(new ListStateDescriptor<Long>("state", Long.class));

        for (Long c : state.get()) {
            count += c;
        }
    }

    // Checkpoint时会调用这个方法，我们要实现具体的snapshot逻辑，比如将哪些本地状态持久化
    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        System.out.println("snapshotState...");
        state.clear();
        state.add(count);
    }

}
