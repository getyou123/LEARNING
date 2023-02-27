package org.example;

import java.io.ObjectStreamException;

public class Thread_Learn10 implements Runnable {
    private int tickets = 100;
    private Object object = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                if (this.tickets < 0) break;
                System.out.println(Thread.currentThread().getName() + "卖出票号" + this.tickets);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.tickets--;
            }
        }
    }

    public static void main(String[] args) {
        Thread_Learn10 threadLearn10 = new Thread_Learn10();
        Thread thread1 = new Thread(threadLearn10, "thread1");
        Thread thread2 = new Thread(threadLearn10, "thread2");
        Thread thread3 = new Thread(threadLearn10, "thread3");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
