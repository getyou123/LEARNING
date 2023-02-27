package org.example;

public class Thread_Learn5 {
    public static void main(String[] args) {
        // 子线程执行时候可以主动放弃cpu
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + "_________" + i);
                    if (i % 100 == 0) {
                        Thread.yield();
                    }
                }
            }
        }).start();

        // 这里的主线程也是会yield
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + "_________" + i);
            if (i % 100 == 0) {
                Thread.yield();
            }
        }
    }
}