package org.getyou123;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread17 {

    /**
     * 无返回值的任务异步执行
     */
    public static void learn1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ":执行任务");
        });
        System.out.println(voidCompletableFuture.get());
    }

    /**
     * 相较于1使用自己自定义的线程池
     */
    public static void learn2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ":执行任务");
        }, executorService);
        System.out.println(voidCompletableFuture.get());
    }


    /**
     * 有返回值且有自己的线程池
     */
    public static void learn3() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ":执行任务");
            return 100;
        }, executorService);
        System.out.println(voidCompletableFuture.get());
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        learn1();
//        learn2();
        learn3();
    }

}
