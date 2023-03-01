package org.example;

import java.math.BigInteger;

public class BigInteger_Learn {
    public static void main(String[] args) {
        BigInteger bigInteger1 = new BigInteger("1234567898765432345676543");
        BigInteger bigInteger2 = new BigInteger("65678765678765456787654345676543456");
        BigInteger add = bigInteger1.add(bigInteger2);// 加和
        System.out.println("加和值：" + add);
        System.out.println("绝对值：" + bigInteger1.abs());
        System.out.println("减法值：" + bigInteger1.min(bigInteger2));
        System.out.println("乘积值：" + bigInteger1.multiply(bigInteger2));
        System.out.println("除法商：" + bigInteger1.divide(bigInteger2));// 只保留整数部分
        System.out.println("取模值：" + bigInteger1.remainder(bigInteger2));// 取模
        System.out.println("取模值数组：" + bigInteger1.divideAndRemainder(bigInteger2)[0] + " " +
                bigInteger1.divideAndRemainder(bigInteger2)[1]);// 返回是数组

        System.out.println("指数值" + bigInteger1.pow(2));// 次方
    }
}
