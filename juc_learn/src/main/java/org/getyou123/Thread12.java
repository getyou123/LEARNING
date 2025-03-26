package org.getyou123;

import java.util.concurrent.TimeUnit;

public class Thread12 {
    // 尝试删除 volatile 关键字观察结果差异
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        // 子线程持续检测 running 状态
        Thread workerThread = new Thread(() -> {
            while (running) {
                // 空循环（模拟持续工作）
            }
            System.out.println("子线程检测到 running=false，终止执行");
        });

        workerThread.start();
        Thread.sleep(1000);  // 主线程暂停1秒
        running = false;     // 修改标志位
        System.out.println("主线程已设置 running=false");
    }
}
