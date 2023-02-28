package org.example;

import org.junit.Test;
import scala.Int;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.StringTokenizer;

public class StringLearn {

    // 关于字符串的存储位置
    @Test
    public void testLocation() {
        String s1 = "for a test"; // 字面量，放在了字符串常量池，s1指向对象
        String s2 = "for a test";
        System.out.println(s1 == s2); // true 都是在常量池

        String s3 = new String("hello");
        String s4 = new String("hello");
        System.out.println(s3 == s4); // false

        s1 = "javaEE";
        s2 = "javaEE";
        s3 = new String("javaEE");
        s4 = new String("javaEE");

        System.out.println(s1 == s2);//true
        System.out.println(s1 == s3);//false
        System.out.println(s1 == s4);//false
        System.out.println(s3 == s4);//false

        // 常量+String对象的拼接是走的StringBuilder，然后调用toString方法
        String s5 = "string " + new String("learn");
        String s6 = "string learn";

        System.out.println(s5 == s6); //false

        // 常量+常量的 就是直接作为常量 final修饰也是一样的常量的
        String s7 = "string " + "learn";
        String s8 = "string learn";

        System.out.println(s7 == s8); // true

        // intern方法
        // 说明：堆空间的对象在调用intern()之后，如果常量池中已有那么直接返回常量池的地址
        // 如果没有的话，放入然后返回地址。
        String s10 = s1.intern();
        System.out.println(s1 == s10);// true


    }

    //创建字符串
    @Test
    public void testCreateString() {
        String s1 = "for a test"; // 字面量，放在了字符串常量池，s1指向对象
        String s2 = "for a test";
        System.out.println(s1 == s2); // 都是在常量池
        s2 = "test";
        System.out.println(s1); // for a test s2使用新的常量，字符串不可变
        String s = new String("test"); // 这就是在堆空间中申请了一块内存,前提是在常量池中有字面量，当然jdk7之后都是在堆中哈
        System.out.println(s);
    }


    // 字符串连接 底层是new String
    @Test
    public void testConcatString() {
        String s1 = "for a test";
        String s = new String("test");
        String concat = s1.concat(s);
        System.out.println(concat);
        System.out.println(s + s1);
    }

    // 转换&编码解码
    @Test
    public void testParse() throws UnsupportedEncodingException {

        String s1 = "100";
        // String -> Integer
        int i = Integer.parseInt(s1);
        System.out.println(i);

        // string -> bytes
        String s2 = "爱你中国";
        byte[] bytes = s2.getBytes("gbk"); // 默认是utf-8
        for (int j = 0; j < bytes.length; j++) {
            System.out.print(bytes[j]);
        }
        System.out.println();

        // bytes->String
        String s = new String(bytes, "utf-8");// 编码和解码的字符集合需要相同
        System.out.println(s);

    }

    // 删除某个位置的字符串
    // 获取子串
    @Test
    public void testPos() {
        //获取子串
        String s1 = "for a test";
        System.out.println("获取子串是不包含左闭右开的" + s1.substring(0, 2));// fo
        String s = new String("test for pos");
        int pos = 1;
        System.out.println(s.substring(0, pos) + s.substring(pos + 1));
    }


    @Test
    // 字符串查找
    public void testIndexOf(){
        // -1 表示没找到
        String s1 = "for a test";
        System.out.println(s1.indexOf('o'));
        System.out.println(s1.lastIndexOf("a"));
        System.out.println(s1.indexOf("a test", 2));//从指定的位置开始找
        System.out.printf("一个串为 %s，一个数为 %d ", "hui", 34);
    }

    public static void main(String[] args) {


        //长度
        {
            String s1 = "for a test";
            System.out.println(s1.length());
        }

        //串内字符循环
        {
            String s1 = "for a test";
            for (int i = 0; i < s1.length(); i++) {
                System.out.println(s1.charAt(i));
            }
        }

        // 构造字符串
        {
            // 线程安全的
            StringBuffer stringBuffer1 = new StringBuffer();
            stringBuffer1.append("1");
            System.out.println(stringBuffer1.toString());

            // 线程不安全的
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("1");
            System.out.println(stringBuilder.toString());
        }

        // 字符串替换
        {
            String str = "Hello World";
            System.out.println(str.replace('H', 'W'));
            System.out.println(str.replaceFirst("He", "Wa"));
            System.out.println(str.replaceAll("He", "Ha"));
        }

        //字符串的翻转，先转化成StringBuffer
        {
            String string = "runoob";
            String reverse = new StringBuffer(string).reverse().toString();
            System.out.println("字符串反转前:" + string);
            System.out.println("字符串反转后:" + reverse);
        }

        //字符串的格式化
        {
            double e = Math.E;
            System.out.format("%f%n", e);
            System.out.format(Locale.CHINA, "%-10.4f%n%n", e);  //指定本地为中国（CHINA）
        }

        //字符串的分割1
        {
            String str = "www-runoob-com";
            String[] temp;
            String delimeter = "-";  // 指定分割字符，有些分隔符需要进行转义
            temp = str.split(delimeter); // 分割字符串
            // 普通 for 循环
            for (int i = 0; i < temp.length; i++) {
                System.out.println(temp[i]);
                System.out.println("");
            }
        }


        //字符串分割2
        {
            String str = "This is String , split by StringTokenizer, created by runoob";
            StringTokenizer st = new StringTokenizer(str);

            System.out.println("----- 通过空格分隔 ------");
            while (st.hasMoreElements()) {
                System.out.println(st.nextElement());
            }

            System.out.println("----- 通过逗号分隔 ------");
            StringTokenizer st2 = new StringTokenizer(str, ",");

            while (st2.hasMoreElements()) {
                System.out.println(st2.nextElement());
            }
        }


    }
}
