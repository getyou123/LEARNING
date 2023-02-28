package org.example;

/**
 * 消费者和生产者
 */

class Consumer extends Thread {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.clerk) {
                if (this.clerk.getProductNum() > 0) {
                    System.out.println(Thread.currentThread().getName() + "消费一个产品");
                    this.clerk.minusProduct();
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.print(Thread.currentThread().getName());
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Producer extends Thread {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.clerk) {
                if (this.clerk.getProductNum() < 20) {
                    System.out.println(Thread.currentThread().getName() + "生产一个产品");
                    this.clerk.addProduct();
//                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.print(Thread.currentThread().getName());
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Clerk {

    private int productNum = 0;//产品数量
    private static final int MAX_PRODUCT = 20;
    private static final int MIN_PRODUCT = 1;

    //增加产品
    public void addProduct() {
        this.productNum++;
        System.out.println("产品增加了一个，变为" + this.productNum);
    }

    public void minusProduct() {
        this.productNum--;
        System.out.println("产品减少了一个,变为" + this.productNum);
    }

    public int getProductNum() {
        return productNum;
    }


}


public class Thread_Learn13 {

    public static void main(String[] args) {

        Clerk clerk1 = new Clerk();
        Consumer consumer = new Consumer(clerk1);
        Producer producer = new Producer(clerk1);

        consumer.setName("消费者");
        producer.setName("生产者");

//        consumer.start();
        producer.start();

    }

}
