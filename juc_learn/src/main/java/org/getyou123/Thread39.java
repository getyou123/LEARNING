package org.getyou123;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class Thread39 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("SynchrinizedLearn");
        SynchrinizedLearn();
        // 1510.78425 ms

        System.out.println("AtomicLongLearn");
        AtomicLongLearn();

        System.out.println("LongAdderLearn");
        LongAdderLearn();

        System.out.println("LongAccumulatorLearn");
        LongAccumulatorLearn();

    }

    /**
     * 使用
     */
    private static void LongAdderLearn() {
        class MyRes {
            LongAdder res = new LongAdder();
            public void addPlus() {
                res.increment();
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(50);
        MyRes myRes = new MyRes();
        // 开始时间
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    myRes.addPlus();
                }
                countDownLatch.countDown();
            }).start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 结束时间
        long endTime = System.nanoTime();
        // 持续时间，纳秒
        long duration = (endTime - startTime);
        // 将纳秒转换为毫秒
        double milliseconds = duration / 1_000_000.0;
        System.out.println("get result:" + myRes.res);
        System.out.println("Method call took " + milliseconds + " ms");
    }

    private static void AtomicLongLearn() {
        class MyRes {
            AtomicLong res = new AtomicLong(0);
            public void addPlus() {
                res.getAndAdd(1);
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(50);
        MyRes myRes = new MyRes();
        // 开始时间
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    myRes.addPlus();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 结束时间
        long endTime = System.nanoTime();
        // 持续时间，纳秒
        long duration = (endTime - startTime);
        // 将纳秒转换为毫秒
        double milliseconds = duration / 1_000_000.0;
        System.out.println("get result:" + myRes.res);
        System.out.println("Method call took " + milliseconds + " ms");
    }

    private static void LongAccumulatorLearn() {
        class MyRes {
            LongAccumulator res = new LongAccumulator((x,y)->x+y,0);
            public void addPlus() {
                res.accumulate(1);
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(50);
        MyRes myRes = new MyRes();
        // 开始时间
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    myRes.addPlus();
                }
                countDownLatch.countDown();
            }).start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 结束时间
        long endTime = System.nanoTime();
        // 持续时间，纳秒
        long duration = (endTime - startTime);
        // 将纳秒转换为毫秒
        double milliseconds = duration / 1_000_000.0;
        System.out.println("get result:" + myRes.res);
        System.out.println("Method call took " + milliseconds + " ms");

    }

    /**
     * 通过SynchrinizedLearn 来实现
     */
    private static void SynchrinizedLearn() {
        class MyRes {
            int res;
            public synchronized void addPlus() {
                res++;
            }
        }

        CountDownLatch countDownLatch = new CountDownLatch(50);
        MyRes myRes = new MyRes();
        // 开始时间
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    myRes.addPlus();
                }
                countDownLatch.countDown();
            }).start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 结束时间
        long endTime = System.nanoTime();
        // 持续时间，纳秒
        long duration = (endTime - startTime);
        // 将纳秒转换为毫秒
        double milliseconds = duration / 1_000_000.0;
        System.out.println("get result:" + myRes.res);
        System.out.println("Method call took " + milliseconds + " ms");
    }
}
