package org.example.tool;


import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

public class ParallelTestSource<T> extends RichParallelSourceFunction<T> implements ResultTypeQueryable<T> {

    private final T[] testStream; // 可以存储任意的某个类型的数组，这个是所有的ParallelSubtask 都共享的
    private final TypeInformation<T> typeInfo; // 具体是哪个类型

    /**
     * 提供按照指定的相同的多个元素来构造的构造器
     * @param events
     */
    @SuppressWarnings("unchecked") // Java中的一个注解，用于在编译时抑制特定类型的警告信息。
    @SafeVarargs // Java中的一个注解，用于在编译时标记某个方法或构造函数使用了可变长度参数，并同时确保该方法或构造函数的实现是安全的。
    public ParallelTestSource(T... events) {
        this.typeInfo = (TypeInformation<T>) TypeExtractor.createTypeInfo(events[0].getClass());
        this.testStream = events;
    }

    /**
     * 继承的RUN方法，run方法是每个 ParallelSubtask 单独发送数据
     * @param ctx
     */
    @Override
    public void run(SourceContext<T> ctx) {
        int indexOfThisSubtask = getRuntimeContext().getIndexOfThisSubtask(); // 用于获取当前subtask在其父任务并行实例中的索引位置。其返回值为一个int类型的数字，表示当前subtask在其父任务并行实例中的位置，范围从0开始到并行度-1。
        int numberOfParallelSubtasks = getRuntimeContext().getNumberOfParallelSubtasks(); // 用于在运行时获取当前 subtask 的并行度。Flink 任务通常会将一个任务分解成多个 subtask 进行并行计算，每个 subtask 都会处理部分数据
        int subtask = 0;

        // the elements of the testStream are assigned to the parallel instances in a round-robin
        // fashion
        for (T element : testStream) {
            if (subtask == indexOfThisSubtask) {
                ctx.collect(element);
            }
            subtask = (subtask + 1) % numberOfParallelSubtasks;
        }

        // test sources are finite, so they emit a Long.MAX_VALUE watermark when they finish
    }

    /**
     * 继承方法
     */
    @Override
    public void cancel() {
        // ignore cancel, finite anyway
    }


    /**
     * 返回数据的类型
     * @return
     */
    @Override
    public TypeInformation<T> getProducedType() {
        return typeInfo;
    }
}

