package org.getyou123;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author getyou123
 * 在读多写少的情况下，提升性能，因为读是共享的，而写是互斥的
 */
public class Thread13 {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // 读操作（允许多线程并发）
    public String get(String key) {
        rwLock.readLock().lock();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String timestamp = sdf.format(new Date());
            System.out.println(timestamp + " " +
                    Thread.currentThread().getName() +
                    " 获取读锁");
            return cache.get(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    // 写操作（互斥执行）
    public void put(String key, String value) {
        rwLock.writeLock().lock();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String timestamp = sdf.format(new Date());
            System.out.println(timestamp + " " +
                    Thread.currentThread().getName() +
                    " 获取写锁");
            cache.put(key, value);
            Thread.sleep(500); // 模拟耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread13 demo = new Thread13();

        // 写线程
        new Thread(() -> demo.put("config", "value1"), "写线程A").start();

        // 读线程组
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                String value = demo.get("config");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String timestamp = sdf.format(new Date());
                System.out.println(timestamp + " " + Thread.currentThread().getName() + " 读取到值: " + value);
            }, "读线程-" + i).start();
        }
    }
}
