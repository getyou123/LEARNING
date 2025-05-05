package org.getyou123;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
class myNumber33 {
    AtomicInteger number = new AtomicInteger();

    public void addPlusPlus() {
        number.getAndIncrement();
    }
}

public class Thread33 {
    public static void main(String[] args) throws InterruptedException {
        myNumber33 myNumber = new myNumber33();
        int SIZE = 50;
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t get result \t" + myNumber.number.get());

    }
}
