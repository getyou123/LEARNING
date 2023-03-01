package org.example;

import java.util.Random;

public class Random_Learn {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println("随机整数：" + r.nextInt());
        System.out.println("随机小数：" + r.nextDouble());
        System.out.println("随机布尔值：" + r.nextBoolean());
    }
}
