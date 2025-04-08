package org.getyou123;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 演示阻塞队列的使用方式
 */
public class Thread14 {
    public static void main(String[] args) {
        // 数组实现
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(6);
        // 生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    // 队列满时自动阻塞
                    queue.put(i);
                    System.out.println("生产: " + i);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    // 队列空时自动阻塞
                    Integer item = queue.take();
                    System.out.println("消费: " + item);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
