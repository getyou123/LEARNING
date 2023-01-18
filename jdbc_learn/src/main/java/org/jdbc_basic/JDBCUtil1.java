package org.jdbc_basic;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * 实现自己的封装jdbc的简单的工具类
 */
public class JDBCUtil1 {
    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    // static 进行初始化
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("/Users/haoguowang/IdeaProjects/LEARNING/jdbc_learn/src/main/resources/jdbc.properties"));
            user = properties.getProperty("username");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void close(ResultSet set_, Statement statement, Connection connection) throws SQLException {
        if (set_ != null) {
            set_.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        JDBCUtil1 jdbcUtil1 = new JDBCUtil1();
        Connection connection = jdbcUtil1.getConnection();
        System.out.println(connection);
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            connection.setAutoCommit(false); // 首先关闭自动提交
            String sql1 = "select  emp_name, emp_position, login_account, login_password from t_emp where emp_id = ?";
            Short emp_id = 3;
            String sql2 = "update  t_emp set login_account = ? where emp_id = ?";
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setShort(1, emp_id);
            ResultSet resultSet = preparedStatement1.executeQuery();
            String loginAccount = resultSet.getString("login_account");
            preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, loginAccount + "add");
            preparedStatement2.setShort(2, emp_id);
            preparedStatement2.executeUpdate();
            connection.commit(); // 这里提交
        } catch (SQLException e) {
            connection.rollback(); // 这里回滚
            throw new RuntimeException(e);
        } finally {
            jdbcUtil1.close(null, preparedStatement1, connection);
            jdbcUtil1.close(null, preparedStatement2, connection);
        }

    }
}
