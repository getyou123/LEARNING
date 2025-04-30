package org.getyou123;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Thread31 {
    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + "come in");
        while (!threadAtomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + " unlock");
        threadAtomicReference.compareAndSet(thread, null);

    }

    public static void main(String[] args) {
        Thread31 thread31 = new Thread31();

        new Thread(() -> {
            thread31.lock();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            thread31.unlock();
        }, "AA").start();

        try {
            TimeUnit.MICROSECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            thread31.lock();
            thread31.unlock();
        }, "BB").start();


    }
}
