package org.example;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){

        User miaowing = new User();
        miaowing.setAge(12);
        miaowing.setSex("男");
        miaowing.setName("小明");
        System.out.println( "Hello World!" );
        System.out.println(miaowing);
        miaowing.Test();


        User xiaohong = new User().setName("xiaohong").setAge(13).setSex("女");
        System.out.println(xiaohong);

    }
}
