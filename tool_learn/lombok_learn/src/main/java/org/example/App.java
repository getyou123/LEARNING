package org.example;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){

        User xiaoming = new User();
        xiaoming.setAge(12);
        xiaoming.setSex("男");
        xiaoming.setName("小明");
        System.out.println( "Hello World!" );
        System.out.println(xiaoming);
        xiaoming.Test();


        User xiaohong = new User().setName("xiaohong").setAge(13).setSex("女");
        System.out.println(xiaohong);

    }
}
