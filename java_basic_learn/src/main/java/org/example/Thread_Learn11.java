package org.example;

public class Thread_Learn11 implements Runnable {
    private int tickets = 100;

    @Override
    public void run() {
        while (true && this.tickets >= 1) {
            sale();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void sale() {
        if (this.tickets >= 1) {
            System.out.println(Thread.currentThread().getName() + "卖出票号" + this.tickets);
            this.tickets--;
        }
    }

    public static void main(String[] args) {
        Thread_Learn11 threadLearn11 = new Thread_Learn11();
        Thread thread1 = new Thread(threadLearn11, "thread1");
        Thread thread2 = new Thread(threadLearn11, "thread2");
        Thread thread3 = new Thread(threadLearn11, "thread3");
        thread2.setPriority(6);
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
