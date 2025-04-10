package org.getyou123;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Thread21 {
    public static void main(String[] args) {
        CompletableFuture<String> playerA = CompletableFuture.supplyAsync(() -> {
            System.out.println("player A come in ");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "playerA";
        });

        CompletableFuture<String> playerB = CompletableFuture.supplyAsync(() -> {
            System.out.println("player B come in ");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "playerB";
        });

        CompletableFuture<String> stringCompletableFuture = playerA.applyToEither(playerB, f -> {
            return f + " is winner";
        });

        System.out.println(Thread.currentThread().getName() + "-----" + stringCompletableFuture.join());

    }
}
