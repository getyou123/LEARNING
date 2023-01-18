package org.jdbc_basic;

import java.sql.*;

public class ResultSetsGet {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1、导入驱动jar包
        //2、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        //3、获取数据库的连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_imperial_court", "root", "123456");

        //4、定义sql语句
        String sql = "select emp_id,emp_name, emp_position, login_account from t_emp";

        //5、获取执行sql查询结果
        Statement stat = con.createStatement();
        ResultSet resultSet = stat.executeQuery(sql);

        while (resultSet.next()) { // 这个就是个光标，可以往下走 next 也可以往上移动 previous
            // while(resultSet.previous()) // 这是往前移动
            int emp_id_1 = resultSet.getInt("emp_id");
            String emp_name_1 = resultSet.getNString("emp_name");
            String emp_position_1 = resultSet.getNString("emp_position");
            String login_account_1 = resultSet.getNString("login_account");
            System.out.println(emp_id_1 + "\t" + emp_name_1 + "\t" + emp_position_1 + "\t" + login_account_1);
        }
        resultSet.close();
        stat.close();
        con.close();
    }

}
