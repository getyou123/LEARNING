package org.getyou123;


/**
 * 现象解释，这个里的如果不设置
 * 设置为守护线程或者判定当前线程是不是守护线程
 * 默认就是非守护线程
 * 线程运行过程中不鞥set为守护线程
 */
public class Thread01 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aaa");
        // 现象解释，这个里的如果不设置,main结束而当前线程不结束，
        // 设置的话因为没有自定义线程了，所以守护线程也是结束的
        thread.setDaemon(true);
        thread.start();

        System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isDaemon());
    }
}
