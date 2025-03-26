package org.getyou123;

import java.util.concurrent.*;

public class Thread10 {
    /**
     * countDownLatch 是一种主从式的工作模式
     */
    public static void countDownLatchLearn() {
        CountDownLatch countDownLatch = new CountDownLatch(7);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                System.out.println("" + Thread.currentThread().getName() + "::线程完工");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
            System.out.println("main 线程收到各个线程完工。main退出");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多executor中，分阶段执行任务的
     */

    public static void cyclicBarrierLearn() {
        int threadCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () ->
                System.out.println("所有线程准备就绪，开始执行主任务")
        );

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 开始执行stage1：准备工作");
                    Thread.sleep((long) (Math.random() * 1000));
                    // 等待其他线程
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 开始执行stage2：主任务");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 信号量机制
     * 多个线程竞争获取有限的资源
     */

    private static void semaphoreLearn() {
        int POOL_SIZE = 3;
        // 公平模式
        Semaphore semaphore = new Semaphore(POOL_SIZE, true);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 模拟10个线程尝试获取连接
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                try {
                    // 获取许可
                    semaphore.acquire();
                    System.out.println("任务 " + taskId + " 获取数据库连接");
                    // 模拟数据库操作
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 必须释放许可
                    semaphore.release();
                    System.out.println("任务 " + taskId + " 释放连接");
                }
            });
        }
        executor.shutdown();
    }


    public static void main(String[] args) {
        semaphoreLearn();
    }
}
