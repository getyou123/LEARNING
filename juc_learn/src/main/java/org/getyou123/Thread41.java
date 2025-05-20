package org.getyou123;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyRes41 {
    ThreadLocal<Integer> num = ThreadLocal.withInitial(() -> 0);

    public void add() {
        num.set(num.get() + 1);
    }

}

public class Thread41 {
    public static void main(String[] args) {
        MyRes41 myRes41 = new MyRes41();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    try {
                        Integer integerBefore = myRes41.num.get();
                        myRes41.add();
                        Integer integerAfter = myRes41.num.get();
                        System.out.println(Thread.currentThread().getName()
                                + "\t"
                                + "before "
                                + integerBefore
                                + "\t"
                                + "after "
                                + integerAfter);
                    } finally {
                        myRes41.num.remove();
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}
