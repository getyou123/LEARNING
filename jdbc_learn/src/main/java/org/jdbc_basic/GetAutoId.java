package org.jdbc_basic;

import java.sql.*;

/**
 * jdbc获取插入数据的id
 */
public class GetAutoId {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_imperial_court", "root", "123456");

        //4、定义sql语句
        String sql = "insert into t_emp(emp_name,emp_position,login_account,login_password) values(?,?,?,?)";

        //5、获取执行sql查询结果
        String emp_name1 = "爱新烨";
        String emp_position = "dnf";
        String login_account = "0xueye999";
        String login_password = "pw";
        PreparedStatement stat = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, emp_name1);// 这里注意从1
        stat.setString(2, emp_position);
        stat.setString(3, login_account);
        stat.setString(4, login_password);
        stat.executeUpdate();
        ResultSet generatedKeys = stat.getGeneratedKeys();
        generatedKeys.next();

        int anInt = generatedKeys.getInt(1);

        System.out.println(anInt);

    }
}
