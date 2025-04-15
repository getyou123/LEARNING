package org.getyou123;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Thread26 {
    public static void main(String[] args) {
//        LockInWaitAndNotify();
//        LockInAwaitAndSignal();
        LockInLockSupport();

    }

    /**
     * 通过LockSupport实现线程的阻塞和唤醒
     */
    private static void LockInLockSupport() {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "  come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "  被唤醒");
        }, "AA");
        aa.start();

        // main暂停一会再去启动线程BB
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "  发送通知");
            LockSupport.unpark(aa);
        }, "BB").start();
    }

    /**
     * 通过condition的Await和Signal
     */
    private static void LockInAwaitAndSignal() {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "  come in");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "  被唤醒");
            } finally {
                reentrantLock.unlock();
            }
        }, "AA").start();

        // main暂停一会再去启动线程BB
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "  发送通知");
                condition.signal();
            } finally {
                reentrantLock.unlock();
            }
        }, "BB").start();
    }

    /**
     * 通过object中的wait和notify
     */
    private static void LockInWaitAndNotify() {
        Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "  come in");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "  被唤醒");
            }
        }, "AA").start();

        // main暂停一会再去启动线程BB
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "  发出通知");
                object.notify();
            }
        }, "BB").start();
    }
}
