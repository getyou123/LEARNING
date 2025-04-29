package org.getyou123;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread29 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 22) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 22) + "\t" + atomicInteger.get());
    }
}
