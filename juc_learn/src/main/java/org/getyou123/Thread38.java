package org.getyou123;

import java.util.concurrent.atomic.LongAdder;

public class Thread38 {
    public static void main(String[] args) {

        baseLongAdder();

    }

    /**
     * 基础使用longAdder方法
     */
    private static void baseLongAdder() {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println(longAdder.sum());
    }
}
