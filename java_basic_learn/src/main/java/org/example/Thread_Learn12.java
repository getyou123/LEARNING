package org.example;

public class Thread_Learn12 implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                notify();
                if (i <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + i++);
                } else
                    break;
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread_Learn12 threadLearn12 = new Thread_Learn12();
        Thread thread1 = new Thread(threadLearn12);
        Thread thread2 = new Thread(threadLearn12);

        thread1.start();
        thread2.start();

    }
}
