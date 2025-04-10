package org.getyou123;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Thread22 {
    public static void main(String[] args) {
        CompletableFuture<Integer> integerCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        });

        CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 20;
        });

        CompletableFuture<Integer> res = integerCompletableFuture1.thenCombine(integerCompletableFuture2, (x, y) -> {
            System.out.println("合并计算中间结果");
            return x + y;
        });

        System.out.println(res.join());
    }
}
