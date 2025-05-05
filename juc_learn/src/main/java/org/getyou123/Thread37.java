package org.getyou123;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 资源类
 */
class MyVar {
    public volatile Boolean flag = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> fieldUpdater =
            AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "flag");

    /**
     * 初始化工作只能一个生效
     * @param myVar
     */
    public void init(MyVar myVar) {
        if (fieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + "\t" + "start init");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "over init");
        } else {
            System.out.println(Thread.currentThread().getName() + "\t" + "try init but not need");
        }
    }

}

public class Thread37 {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                myVar.init(myVar);
            }).start();
        }
    }
}
