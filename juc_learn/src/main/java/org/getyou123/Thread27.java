package org.getyou123;

import java.util.concurrent.TimeUnit;

public class Thread27 {
//    static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "得知被修改为false");
        }, "AA").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        flag = false;
        System.out.println(Thread.currentThread().getName() + "修改为false");

    }
}
