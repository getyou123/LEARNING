package org.example;

public class AbstractClass01Impl extends AbstractClass01 {
    @Override
    public String toString() {
        return "AbstractClass01Impl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public void sound() {
        System.out.println("sound");
    }
}
