package org.getyou123;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Data
@AllArgsConstructor
@NoArgsConstructor
class myAcountForSyn {
    String name = "bbC";
    int money;

    public synchronized void addForSyn() {
        money++;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class myAcountForFieUp {
    String name = "nnv";
    // public volatile 修饰
    public volatile int money;
    // AtomicIntegerFieldUpdater
    AtomicIntegerFieldUpdater<myAcountForFieUp> fieldUpdater
            = AtomicIntegerFieldUpdater.newUpdater(myAcountForFieUp.class,"money");

    public void addMoney(myAcountForFieUp myAcountForFieUp){
        fieldUpdater.getAndIncrement(myAcountForFieUp);

    }
}

public class Thread36 {
    private static void workWithSyn() {
        myAcountForSyn bbc = new myAcountForSyn("bbc", 0);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    bbc.addForSyn();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "get result " + bbc.money);
    }

    public static void main(String[] args) {
        // 使用较重的synchronized
        workWithSyn();
        // 使用较轻的fieldUpdater
        workWithFieUp();

    }

    private static void workWithFieUp() {
        myAcountForFieUp myAcountForFieUp = new myAcountForFieUp();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myAcountForFieUp.addMoney(myAcountForFieUp);
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "get result " + myAcountForFieUp.money);
    }
}
