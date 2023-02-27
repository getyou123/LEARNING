package org.example;

public class Thread_Learn9 implements Runnable {
    // 100 tickets
    private Integer tickets = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this.tickets) {
                if (this.tickets < 0) break;
                System.out.println(Thread.currentThread().getName() + "卖出票号" + this.tickets);
                this.tickets--;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread_Learn9 threadLearn9 = new Thread_Learn9();
        Thread thread1 = new Thread(threadLearn9, "thread1");
        Thread thread2 = new Thread(threadLearn9, "thread2");
        Thread thread3 = new Thread(threadLearn9, "thread3");

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
