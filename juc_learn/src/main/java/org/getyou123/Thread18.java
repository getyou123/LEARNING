package org.getyou123;

import org.omg.CORBA.TIMEOUT;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Thread18 {
    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行开始");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "执行结束");
            return 10;
        }).whenComplete((v, e) -> {
            System.out.println("计算完成的结果是" + v.toString());
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });


    }
}
