package org.example;

//只包含常量和抽象方法的抽象类，在java中父类的继承只一个，但是可以实现多个接口
public interface InterFace01 {
    //即使不写也是public static final
    int i = 0;

    //即使不写也是public abstract
    void sound();

    // 静态方法
    static void souundAct() {
        System.out.println("静态方法");
    }

    // 默认方法
    public default void sound1(){
        System.out.println("默认的方法");
    }

}
