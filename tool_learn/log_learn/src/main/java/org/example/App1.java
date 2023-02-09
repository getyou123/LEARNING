package org.example;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
/**
 * Hello world!
 */

@Slf4j
public class App1 {

    public static void main(String[] args) {

        for (int i = 0; i < 1000000; i++) {
            MDC.put("TraceId", "123456");
            log.debug("Hello World!");
            log.info("Hello World!");
            log.warn("Hello World!");
            log.error("Hello World!");
            new DemoLog().log("Hello World!");
            MDC.clear();
        }

    }
}
