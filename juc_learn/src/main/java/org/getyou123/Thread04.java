package org.getyou123;

/**
 * 2个线程，分别对同一个变量+1-1交替
 */
public class Thread04 {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
    }
}

class Share {
    // 初始值
    private int num = 0;

    // +1方法
    public synchronized void incr() throws InterruptedException {
        while (0 != num) {
            // 等待
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "::" + num);
        // 通知
        this.notifyAll();
    }

    // -1方法
    public synchronized void decr() throws InterruptedException {
        while (1 != num) {
            // 等待
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "::" + num);
        // 通知
        this.notifyAll();
    }
}
