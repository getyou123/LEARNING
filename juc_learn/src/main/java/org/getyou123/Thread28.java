package org.getyou123;


import java.util.concurrent.TimeUnit;

class MyNumber {
    public volatile int number;

    public void addPlusPlus() {
        number++;
    }
}

public class Thread28 {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(myNumber.number);

    }
}
