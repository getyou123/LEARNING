package org.getyou123;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareInThread6 {
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (1 != flag) {
                c1.await();
            }
            // 干活
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + "::" + j + ":轮数:" + loop);
            }
            flag = 2;
            c2.signal();
        } finally {
            // 释放
            lock.unlock();
        }
    }

    public void print10(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (2 != flag) {
                c2.await();
            }
            // 干活
            for (int j = 0; j < 10; j++) {
                System.out.println(Thread.currentThread().getName() + "::" + j + ":轮数:" + loop);
            }
            flag = 3;
            c3.signal();
        } finally {
            // 释放
            lock.unlock();
        }
    }

    public void print15(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (3 != flag) {
                c3.await();
            }
            // 干活
            for (int j = 0; j < 15; j++) {
                System.out.println(Thread.currentThread().getName() + "::" + j + ":轮数:" + loop);
            }
            flag = 1;
            c1.signal();
        } finally {
            // 释放
            lock.unlock();
        }
    }
}

public class Thread6 {
    public static void main(String[] args) {
        ShareInThread6 shareInThread6 = new ShareInThread6();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInThread6.print5(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInThread6.print10(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    shareInThread6.print15(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"CC").start();
    }
}
