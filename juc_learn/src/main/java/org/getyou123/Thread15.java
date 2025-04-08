package org.getyou123;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通过线程池进行线程管理
 */
public class Thread15 {
    public static void main(String[] args) {
        // 自定义线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // corePoolSize
                4,
                // maximumPoolSize
                5,
                // keepAliveTime
                60, TimeUnit.SECONDS,
                // 有界队列（容量100）
                new ArrayBlockingQueue<>(100),
                // 默认线程工厂
                Executors.defaultThreadFactory(),
                // 拒绝策略
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 提交任务
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("执行任务: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        // 关闭线程池
        executor.shutdown();  // 平滑关闭（等待任务完成）
    }
}
