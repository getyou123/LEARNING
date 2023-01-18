package org.jdbc_basic;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class AppDruidL {
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        ClassLoader classLoader = AppDruidL.class.getClassLoader();
        // 2、通过类加载器对象从类路径根目录下读取文件
        InputStream stream = classLoader.getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(stream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        System.out.println("创建成功连接池");
        Connection connection = dataSource.getConnection();
        System.out.println("成功获取到jdbc连接");
        connection.close();
    }
}
