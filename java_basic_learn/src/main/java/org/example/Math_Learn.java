package org.example;

public class Math_Learn {
    public static void main(String[] args) {
        // 绝对值
        double d1 = Math.abs(-5); //d1的值为5
        double d2 = Math.abs(5); //d2的值为5

        // 向上取整
        System.out.println(Math.ceil(3.3));//d1的值为 4.0
        System.out.println(Math.ceil(-3.3)); //d2的值为 -3.0
        System.out.println(Math.ceil(5.1)); //d3的值为 6.0

        // 向下取整
        System.out.println(Math.floor(3.3)); //d1的值为3.0
        System.out.println(Math.floor(-3.3)); //d2的值为-4.0
        System.out.println(Math.floor(5.1)); //d3的值为 5.0

        // 四舍五入
        System.out.println(Math.round(5.5)); //d1的值为6
        System.out.println(Math.round(5.4)); //d2的值为5
        System.out.println(Math.round(-3.3)); //d3的值为-3
        System.out.println(Math.round(-3.8)); //d4的值为-4


        //pow(double a,double b)：返回a的b幂次方法
        System.out.println(Math.pow(2,4));
        System.out.println(Math.sqrt(9));//sqrt(double a)：返回a的平方根
        System.out.println(Math.random());//random()`：返回[0,1)的随机值
        System.out.println(Math.PI); // 圆周率
        System.out.println(Math.max(12,10));//返回x,y中的最大值
        System.out.println(Math.min(10, 12));//返回x,y中的最小值

    }
}
