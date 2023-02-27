package org.example;

public class Thread_Learn8 implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("我一直守护者你...");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread_Learn8 m = new Thread_Learn8();
        Thread thread = new Thread(m);
        thread.setDaemon(true);
        thread.start();

        // 守护线程直到所有的线程结束然后自己结束
        for (int i = 1; i <= 100; i++) {
            System.out.println("main:" + i);
        }
    }
}
