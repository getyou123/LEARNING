package org.example;

import java.math.BigDecimal;


public class BigDecimal_Learn {
    public static void main(String[] args) {
        BigDecimal bigDecimal1 = new BigDecimal("1233333333333.3333");
        BigDecimal bigDecimal2 = new BigDecimal("678987654567876.2344444444444");

        System.out.println("加和值：" + bigDecimal1.add(bigDecimal2));
        System.out.println("减法值：" + bigDecimal1.subtract(bigDecimal2));
        System.out.println("除法值：" + bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_UP)); // 保留两位小数 （ROUND_UP :向上加1、ROUND_DOWN :直接舍去、ROUND_HALF_UP:四舍五入）
        System.out.println("乘积值：" + bigDecimal1.multiply(bigDecimal2));

    }
}
