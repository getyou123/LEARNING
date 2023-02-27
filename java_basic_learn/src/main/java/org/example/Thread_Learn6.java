package org.example;

public class Thread_Learn6 implements Runnable {
    public static void main(String[] args) {
        Thread_Learn6 threadLearn6 = new Thread_Learn6();
        Thread thread = new Thread(threadLearn6);
        // 主线程开启子线程
        thread.start();
        // 主线程自己的活
        for (int i = 0; i < 10001; i++) {
            System.out.println(Thread.currentThread().getName() + "======" + i);
            // 主线程到9999等待子进程结束
            if (i % 10000 == 9999) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "======" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
