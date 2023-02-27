package org.example;

public class Thread_Learn1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "**********" + i);
        }
    }

    public static void main(String[] args) {
        // 创建一个对象
        Thread_Learn1 treadLearn1 = new Thread_Learn1();
        // 开启start
        treadLearn1.start();
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "**********" + i);
        }
    }
}
