package org.getyou123;

import java.util.concurrent.TimeUnit;

public class Thread24 {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " 检测到需要中断");
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " working and not stop");
            }
        }, "AA");
        aa.start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            aa.interrupt();
        }, "BB").start();
    }
}
