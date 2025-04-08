package org.getyou123;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Thread16 {


    /**
     * 阻塞等待
     */
    public static void waitInBlockingLearn() {
        // worker
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(() -> {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行任务");
            }
            return 1;
        });
        new Thread(integerFutureTask, "AA").start();
        try {
            while (!integerFutureTask.isDone()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("等待任务完成");
            }
            System.out.println(integerFutureTask.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            integerFutureTask.cancel(true);
        }
    }

    /**
     * 阻塞等待一段时间
     */
    public static void waitInBlockingForAWhileLearn() {
        // worker
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(() -> {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行任务");
            }
            return 1;
        });
        new Thread(integerFutureTask, "AA").start();
        try {
            Integer integer = integerFutureTask.get(5, TimeUnit.SECONDS);
            System.out.println("任务完成，获取到结果" + integer);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("等待超时了!!!");
            throw new RuntimeException(e);
        } finally {
            integerFutureTask.cancel(true);
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 一直阻塞的版本
//        waitInBlockingLearn();
        // 有最大等待时长的版本
        waitInBlockingForAWhileLearn();


    }


}

