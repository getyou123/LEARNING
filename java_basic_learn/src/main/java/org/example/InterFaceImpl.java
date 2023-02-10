package org.example;

import java.util.HashSet;

public class InterFaceImpl implements InterFace01, InterFace02 {
    @Override
    public void sound() {
        System.out.println("实现了第一个接口的方法");
    }

    @Override
    public void run() {
        System.out.println("实现了第二个接口的方法");
    }

}
