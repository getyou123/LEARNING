package org.getyou123;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过lock实现 +1 -1
 * 判断等待条件，干活，释放
 */
class ShareInLock {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int num = 0;

    public void incr() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (0 != num) {
                condition.await();
            }
            // 干活
            num++;
            System.out.println(Thread.currentThread().getName() + "::" + num);
            // 释放
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (1 != num) {
                condition.await();
            }
            // 干活
            num--;
            System.out.println(Thread.currentThread().getName() + "::" + num);
            // 释放
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class Thread5 {
    public static void main(String[] args) {
        ShareInLock shareInLock = new ShareInLock();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInLock.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInLock.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInLock.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"CC").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInLock.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"DD").start();
    }
}
