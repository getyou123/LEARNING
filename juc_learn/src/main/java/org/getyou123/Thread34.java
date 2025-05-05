package org.getyou123;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Thread34 {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个长度为5的原子整数数组，初始值为0
        AtomicIntegerArray atomicArray = new AtomicIntegerArray(5);

        // 创建3个线程，每个线程对数组的不同索引进行递增操作
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                // 每个线程对数组的某个固定索引进行1000次递增
                int index = threadId % atomicArray.length(); // 确保索引在数组范围内
                for (int j = 0; j < 1000; j++) {
                    atomicArray.getAndIncrement(index); // 原子递增操作
                }
                System.out.println("Thread " + threadId + " 完成操作");
            });
        }

        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程执行完毕
        for (Thread thread : threads) {
            thread.join();
        }

        // 输出最终数组结果
        System.out.println("\n最终数组内容:");
        for (int i = 0; i < atomicArray.length(); i++) {
            System.out.println("atomicArray[" + i + "] = " + atomicArray.get(i));
        }
    }
}
