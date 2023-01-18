package org.jdbc_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCDemo1 {
    public static void main(String[] args) throws Exception {   //下面方法有不同的异常，我直接抛出一个大的异常

        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_imperial_court", "root", "123456");

        //4、定义sql语句
        String sql = "update t_emp set emp_name = '索额图' where login_account = 'tutu1636' ";

        //5、获取执行sql语句的对象
        Statement stat = con.createStatement();

        //6、执行sql并接收返回结果
        int count = stat.executeUpdate(sql);

        //7、处理结果
        System.out.println(count);

        //8、释放资源
        stat.close();
        con.close();
    }
}