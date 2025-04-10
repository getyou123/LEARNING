package org.getyou123;

import java.util.concurrent.*;

public class Thread20 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * thenApply 和 thenAccept的区别
         * thenApply 返回的是CompletableFuture<T>
         *     thenAccept 返回的是CompletableFuture<Void> 并没有返回值
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello thenApply")
                //  这个是在异步任务处理完成之后 又返回值 可以多次链式调用
                .thenApply(s -> s.toUpperCase())
                .thenApply(s -> s + "!");
        // 输出 "HELLO"
        System.out.println(future.join());

        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> "hello thenAccept")
                //  这个是在异步任务处理完成之后 并无返回值
                .thenAccept(result -> System.out.println(result));
        future1.join();


        /**
         * exceptionally 捕获异常
         */
        CompletableFuture<Integer> safeFuture = CompletableFuture.supplyAsync(() -> {
            int res = 10 / 0;
            return res;
        }).exceptionally(ex -> {
            System.out.println("捕获异常: " + ex.getMessage());
            // 返回默认值
            return 0;
        });
        // 输出 0
        System.out.println(safeFuture.join());


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        /**
         * 任务编排
         * allOf 等待所有任务完成
         * anyOf 等待任意一个任务完成
         */
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello";
        }, executorService);

        CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);
        all.thenRun(() -> System.out.println("所有任务完成！"));

        executorService.shutdown();

    }


}