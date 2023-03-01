package org.example;

public class System_Learn {
    public static void main(String[] args) {
        // 获取系统时间戳
        long l = System.currentTimeMillis();
        System.out.println("系统时间戳" + l);

        // 获取系统变量  获取某些key
        String javaHome = System.getProperty("java.home");
        System.out.println("java.home:" + javaHome);
        System.out.println("java.version:" + System.getProperty("java.version"));
        System.out.println("os.version:" + System.getProperty("os.version"));
        System.out.println("os.name:" + System.getProperty("os.name"));
        System.out.println("user.name:" + System.getProperty("user.name"));
        System.out.println("user.home:" + System.getProperty("user.home"));
        System.out.println("user.dir:" + System.getProperty("user.dir"));

        // 试图启动full gc
        System.gc();

        // 0-正常 非零-异常
        System.exit(0);
    }
}
