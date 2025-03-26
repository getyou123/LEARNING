package org.getyou123;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketInFairAndNOFair {
    private int num = 10;
    private Lock saleLock = new ReentrantLock(true);

    public void sale() {
        this.saleLock.lock();
        try {
            if(num>0){
                System.out.println(Thread.currentThread().getName() + "卖出第" + num + "票");
                num--;
            }
        } finally {
            this.saleLock.unlock();
        }
    }
}
public class Thread11 {
    public static void main(String[] args) {
        TicketInFairAndNOFair ticketInFairAndNOFair = new TicketInFairAndNOFair();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                ticketInFairAndNOFair.sale();
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                ticketInFairAndNOFair.sale();
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                ticketInFairAndNOFair.sale();
            }
        },"CC").start();

    }
}
