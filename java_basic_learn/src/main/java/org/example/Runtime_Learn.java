package org.example;

public class Runtime_Learn {
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        // 返回 Java 虚拟机中初始化时的内存总量。此方法返回的值可能随时间的推移而变化，
        // 这取决于主机环境。默认为物理电脑内存的1/64。
        long l = runtime.totalMemory();

        // 返回 Java 虚拟机中最大程度能使用的内存总量。默认为物理电脑内存的1/4。
        long l1 = runtime.maxMemory();

        // 返回 Java 虚拟机中的空闲内存量。调用 gc 方法可能导致 freeMemory 返回值的增加。
        long l2 = runtime.freeMemory();

        System.out.println("l:" + l);
        System.out.println("l1:" + l1);
        System.out.println("l2:" + l2);

    }
}
