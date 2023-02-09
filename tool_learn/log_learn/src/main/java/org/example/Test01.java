package org.example;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class Test01 {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //在类中引入注解@Slf4j后就可以使用，使用时固定的记录器是log
        log.info("Hello World --by Test01， Test01：测试日志1  ");

        new Timer("timer").schedule(new TimerTask() {
            @Override
            public void run() {
                log.error("Hello World --by Test01， Test01：测试日志1 ,系统时间 " + sdf.format(System.currentTimeMillis()));
                log.error("参数1:{},参数2：{}", "lombok时间", sdf.format(System.currentTimeMillis()));
            }
        }, 1000, 1000);
    }
}