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
     * 必 须确 保 由 单 独 线 程 运 行 的 S o u r c e F u n c t i o n . r u n () 方法 不会 在 检 查点生成过程中(即CheckpointedFunction.snapshot tate()调用期间)向前
     * 推 进 偏 移 和 发 出 数 据 。 为 此 ， 可 以 通 过 S o u r c e C o n t e x t . g e t C h e c k p o i n t L o c k () 方法获取 一个锁对象，
     * 并用它对run()方法中推进偏移和发出数据的代码块 进行同步处理
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
