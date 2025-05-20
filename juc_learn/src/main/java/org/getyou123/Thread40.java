package org.getyou123;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

class MyRes {
    public int num = 0;
    //        synchronized 实现不在展示
    ThreadLocal<Integer> res = ThreadLocal.withInitial(() -> 0);

    public MyRes() {
    }

    public void addWithThreadLocal() {
        res.set(res.get() + 1);
    }

}

public class Thread40 {

    public static void main(String[] args) {
        MyRes myRes = new MyRes();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {

                try {
                    int randomSize = new Random().nextInt(5) + 1;
                    for (int j = 0; j < randomSize; j++) {
                        myRes.addWithThreadLocal();
                    }
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName() + "\t" + myRes.res.get());
                } finally {
                    myRes.res.remove();
                }

            }, String.valueOf(i)).start();

        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("all added to " + "\t" + myRes.res.get());

    }
}
