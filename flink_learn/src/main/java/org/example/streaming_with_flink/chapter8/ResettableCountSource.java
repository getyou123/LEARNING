package org.example.streaming_with_flink.chapter8;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * 自定义可以重置的数据源
 * 需要实现 CheckpointedFunction接口
 * 注意这里是串行的source
 */
public class ResettableCountSource implements CheckpointedFunction, SourceFunction<Long> {
    Boolean isRunning = true;
    Long cnt = 0L;
    ListState<Long> offsetState = null;

    /**
     * 数据输出逻辑
     * 必须确保由单独线程运行的SourceFunction.run()方法不会在检查点生成过程中(即CheckpointedFunction.snapshottate()调用期间)向前
     * 推进偏移和发出数据。为此，可以通过SourceContext.getCheckpointLock()方法获取一个锁对象，
     * 并用它对run()方法中推进偏移和发出数据的代码块进行同步处理
     *
     * @param ctx The context to emit elements to and for accessing locks.
     * @throws Exception
     */
    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (isRunning && cnt < Long.MAX_VALUE) {
            synchronized (ctx.getCheckpointLock()) { // 这里获取同步锁
                cnt += 1;
                ctx.collect(cnt);
            }
        }
    }

    /**
     * sourceFunction接口
     */
    @Override
    public void cancel() {
        isRunning = false;
    }

    /**
     * 初始化操作
     *
     * @param context the context for initializing the operator
     * @throws Exception
     */
    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        ListStateDescriptor<Long> desc = new ListStateDescriptor<>("offset", Long.class);
        offsetState = context.getOperatorStateStore().getListState(desc);
        // 初始化cnt
        Iterable<Long> longs = offsetState.get();
        cnt = (null == longs || !longs.iterator().hasNext()) ? -1L : longs.iterator().next();
    }

    /**
     * 检查点生成时候的操作
     *
     * @param context the context for drawing a snapshot of the operator
     * @throws Exception
     */
    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        // 删除之前的cnt
        offsetState.clear();
        // 添加新的cnt
        offsetState.add(cnt);
    }
}
