package org.example;

public class Thread_Learn7 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "-------" + i + Thread.currentThread().getPriority());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Thread_Learn7 threadLearn7 = new Thread_Learn7();
        Thread thread = new Thread(threadLearn7);
        // 获取优先级
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();


    }
}
