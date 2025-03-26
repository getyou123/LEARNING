package org.getyou123;

import java.util.concurrent.TimeUnit;

public class Thread09 {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(()->{
            synchronized (a){
                System.out.println("获取到了a,尝试获取b");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b){
                    System.out.println("获取到了a,b");
                }
            }
        },"AA").start();

        new Thread(()->{
            synchronized (b){
                System.out.println("获取到了b,尝试获取a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a){
                    System.out.println("获取到了a,b");
                }
            }
        },"BB").start();
    }
}
