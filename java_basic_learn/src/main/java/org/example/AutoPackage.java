package org.example;

public class AutoPackage {

    public static void main(String[] args) {
        //自动拆箱
        int i = 2;
        Integer integer = i;//自动装箱
        int i1 = integer;//自动拆箱

        //和string之间转化
        String intStr = "123";
        int i3 = Integer.parseInt(intStr);
        System.out.println(i3 + 1);


        String intStr1 = String.valueOf(i3);
        System.out.println(intStr1 + 1);

        //object的toString的默认是输出内存的地址。可以自己实现重写toString方法

    }

}
