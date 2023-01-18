package org.jdbc_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Transaction01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_imperial_court", "root", "123456");

    }

}
