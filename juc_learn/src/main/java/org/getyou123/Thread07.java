package org.getyou123;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Thread07 {
    public static void unSafeAdd() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(arrayList);
            }, String.valueOf(i)).start();
        }
    }

    public static void safeAddWithVector() {
        Vector<String> arrayList = new Vector<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(arrayList);
            }, String.valueOf(i)).start();
        }
    }

    public static void safeAddWithCopyOnWriteArrayList() {
        List<String> arrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(arrayList);
            }, String.valueOf(i)).start();
        }
    }

    public static void main(String[] args) {
        // 不安全示例
//        unSafeAdd();
        // 安全示例，不建议使用
//        safeAddWithVector();

        // 安全实例建议使用
        safeAddWithCopyOnWriteArrayList();
    }
}
