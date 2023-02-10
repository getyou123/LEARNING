package org.example;

import java.util.Locale;
import java.util.StringTokenizer;

public class StringLearn {
    public static void main(String[] args) {

        //创建字符串
        {
            String s1 = "for a test";
            System.out.println(s1);
            String s = new String("jiushishuo");
            System.out.println(s);
        }

        // 字符串连接
        {
            String s1 = "for a test";
            String s = new String("jiushishuo");
            String concat = s1.concat(s);
            System.out.println(concat);
            System.out.println(s + s1);
        }

        // 删除某个位置的字符串
        // 获取子串
        {
            //获取子串
            String s1 = "for a test";
            System.out.println("获取子串是不包含左闭右开的" + s1.substring(1, 2));
            String s = new String("jiushishuo");
            int pos = 1;
            System.out.println(s.substring(0, pos) + s.substring(pos + 1));
        }

        // 字符串查找
        {
            // -1 表示没找到
            String s1 = "for a test";
            System.out.println(s1.indexOf('o'));
            System.out.println(s1.lastIndexOf("a"));
            System.out.println(s1.indexOf("a test", 2));//从指定的位置开始找
            System.out.printf("一个串为 %s，一个数为 %d ", "hui", 34);
        }

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
