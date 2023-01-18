package org.jdbc_basic;

import java.sql.*;

/**
 * PrepareStatement主要用来进行防止sql注入
 */
public class PrepareStatementLearn {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_imperial_court", "root", "123456");

        //5、获取执行sql语句的对象 一条sql一个prepareStatement
        // preparedStatement 主要是两个方法
        // 如果是查询的话就是  preparedStatement.executeQuery()
        // 如果是更新的话的话就是  preparedStatement.executeUpdate()

        /**
         * 实现数据查询dml
         */
        String sql = "select emp_id,emp_name, emp_position, login_account from t_emp where emp_id < ?";
        Short emp_id_0 = 3;
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setShort(1, emp_id_0); // 这里注意这里的下标是从1开始的
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            // 这个就是个光标
            int emp_id_1 = resultSet.getInt("emp_id");
            String emp_name_1 = resultSet.getNString("emp_name");
            String emp_position_1 = resultSet.getNString("emp_position");
            String login_account_1 = resultSet.getNString("login_account");
            System.out.println(emp_id_1 + "\t" + emp_name_1 + "\t" + emp_position_1 + "\t" + login_account_1);
        }

        /**
         * 实现dml数据更新update
         */
        String sql1 = "update t_emp set emp_name=? where emp_id = ?";
        String emp_name1 = "爱新觉罗.玄烨";
        Short emp_id = 1;
        PreparedStatement preparedStatement1 = con.prepareStatement(sql1);
        preparedStatement1.setString(1, emp_name1);// 这里注意从1
        preparedStatement1.setShort(2, emp_id);
        int res = preparedStatement1.executeUpdate();
        if (res > 0) {
            System.out.println("成功更新");
        } else {
            System.out.println("更新失败");
        }


        // 关闭资源等操作
        resultSet.close();
        preparedStatement.close();
        con.close();


    }
}
