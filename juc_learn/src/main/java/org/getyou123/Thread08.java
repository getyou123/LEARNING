package org.getyou123;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class Thread08 {
    /**
     * 不安全方式读取hashset
     */
    public static void unSafeHashSet() {
        HashSet<String> strings = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }
    public static void safeCopyOnWriteArraySet() {
        CopyOnWriteArraySet<String> strings = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }

    public static void main(String[] args) {
//        unSafeHashSet();
        safeCopyOnWriteArraySet();
    }



}
