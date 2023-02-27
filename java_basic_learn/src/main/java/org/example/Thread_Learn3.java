package org.example;

public class Thread_Learn3 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "**********" + i);
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Thread_Learn3());
        thread.start();
    }
}
