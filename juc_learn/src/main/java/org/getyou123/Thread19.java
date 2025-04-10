package org.getyou123;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class netMall {
    private String name;

    public netMall(String name) {
        this.name = name;
    }

    public String caculate(String booName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("%s in %s is %.2f", booName, name, Math.random() * 100 / 1.3);
    }

}


public class Thread19 {
    static List<netMall> malls = Arrays.asList(
            new netMall("jd"),
            new netMall("tb"),
            new netMall("pdd")
    );

    private static List<String> getPrice(String name) {
        return
                malls.stream().map(mall -> {
                    return mall.caculate(name);
                }).collect(Collectors.toList());
    }

    private static List<String> getPriceAsync(String name) {
        return
                malls
                        .stream()
                        .map(
                                mall -> {
                                    return
                                            CompletableFuture.supplyAsync(() -> mall.caculate(name));
                                }

                        ).collect(Collectors.toList())
                        .stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> mysqlList = getPrice("mysql");
        for (String s : mysqlList) {
            System.out.println(s);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("cost time:" + (endTime - startTime));

        long startTime1 = System.currentTimeMillis();
        List<String> mysqlList1 = getPriceAsync("mysql");
        for (String s : mysqlList1) {
            System.out.println(s);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("cost time:" + (endTime1 - startTime1));


    }


}
