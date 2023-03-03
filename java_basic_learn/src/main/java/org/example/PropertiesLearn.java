package org.example;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesLearn {

    // 获取系统的配置文件
    @Test
    public void test01() {
        Properties properties = System.getProperties();
        System.out.println("所有的系统的配置信息：" + properties);
        String fileEncoding = properties.getProperty("file.encoding");//当前源文件字符编码
        System.out.println("fileEncoding = " + fileEncoding);
    }

    // 在Properties中设置配置
    @Test
    public void test02() {
        Properties properties = new Properties();
        properties.setProperty("user", "XX");
        properties.setProperty("HADOOP_HOME", "/spark");
        properties.setProperty("password", "213");
        System.out.println(properties);
    }

    // 从配置文件中读取数据
    @Test
    public void test03() throws IOException {
        Properties pros = new Properties();
        pros.load(PropertiesLearn.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        String user = pros.getProperty("user");
        System.out.println(user);
    }


    public static void main(String[] args) throws IOException {


    }
}
