package org.example.streaming_with_flink.chapter8;

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.runtime.operators.CheckpointCommitter;
import org.apache.flink.streaming.runtime.operators.GenericWriteAheadSink;

/**
 * 数据被收集到检查点中
 * 然后对外部系统至少一次写入（正常运行则是精准一次性写入
 */
public class StdOutWriteAheadSink extends GenericWriteAheadSink<Long> {

    public StdOutWriteAheadSink(CheckpointCommitter committer, TypeSerializer<Long> serializer, String jobID) throws Exception {
        super(committer, serializer, jobID);
    }

    /**
     * 核心实现方法是这个，这个方法在检查点完成时进行调用，数据写入到外部，成功返回true，失败返回false
     * @param values The values to be written
     * @param checkpointId The checkpoint ID of the checkpoint to be written
     * @param timestamp The wall-clock timestamp of the checkpoint
     * @return 是否产出成功
     * @throws Exception
     */
    @Override
    protected boolean sendValues(Iterable<Long> values, long checkpointId, long timestamp) throws Exception {
        for (Long value : values) {
            System.out.println(value);
        }
        return true;
    }
}