package org.getyou123;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * 演示三个线程
 * 可重入锁的同步
 */
public class Thread02Lock {
    public static void main(String[] args) {
        TicketForReentrantLock ticket = new TicketForReentrantLock();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                ticket.sale();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                ticket.sale();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BBB").start();


        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                ticket.sale();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CCC").start();
    }
}


/**
 * 资源类本身持有锁这里演示的
 * 是可重入锁
 */
class TicketForReentrantLock {
    /**
     * 可重入锁
     * 多个线程可以按照申请 使用 释放的顺序依次使用
     */
    public final static ReentrantLock lock = new ReentrantLock();
    public int nums = 20;

    /**
     * 资源类的在多线程之间调用的方式
     */
    public void sale() {
        lock.lock(); // 上锁
        try {
            if (nums > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出" + (nums--) + "剩余" + nums);
            }
        } finally {
            lock.unlock(); // 无论是否存在异常都释放锁
        }
    }
}
