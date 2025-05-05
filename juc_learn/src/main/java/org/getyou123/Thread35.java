package org.getyou123;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class Thread35 {
    public static void main(String[] args) {
        AtomicMarkableReference<Integer> integerAtomicMarkableReference = new AtomicMarkableReference<>(10, false);

        // AA swap
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t" + "come in");
            while (integerAtomicMarkableReference.compareAndSet(10,20,false,true)) {
                System.out.println(Thread.currentThread().getName() + "\t" + "success swap");
            }
        },"AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // main 再次swap
        if(integerAtomicMarkableReference.compareAndSet(20,10,true,false)) {
            System.out.println(Thread.currentThread().getName() + "\t" + "success swap");
        }


    }
}
