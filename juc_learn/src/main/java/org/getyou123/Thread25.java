package org.getyou123;

import java.util.concurrent.TimeUnit;

public class Thread25 {

    /**
     * 演示InterruptedException
     */
    private static void InterruptedExceptionLearn() {
        Thread aa = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "抛出异常");
                throw new RuntimeException(e);
            }
        }, "AA");
        aa.start();

        aa.interrupt();
    }


    /**
     * Interrupt不工作的案例
     */
    private static void InterruptNotWork() {
        Thread aa = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
        }, "AA");

        aa.start();
        aa.interrupt();
    }

    /**
     * 演示响应中断
     */
    private static void InterruptWork() {
        Thread aa = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "响应中断 停止线程");
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " working");
            }
        }, "AA");

        aa.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        aa.interrupt();
    }


    public static void main(String[] args) {
//        InterruptedExceptionLearn();
//        InterruptNotWork();

        InterruptWork();

    }


}
