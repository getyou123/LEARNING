package org.jdbc_basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 如何使用批处理操作mysql，注意链接需要更新
 * 首先是数据库支持批处理
 * addBatch() 方法用于添加单个语句到批处理。
 * executeBatch() 方法用于启动执行所有组合在一起的语句，返回一个整数数组，数组中的每个元素代表了各自的更新语句的更新数目。
 * 用 clearBatch() 方法删除它们
 * 下面实现1000条插入
 */

public class BatchProcess {
    public static void main(String[] args) throws SQLException {
        JDBCUtil1 jdbcUtil1 = new JDBCUtil1();
        Connection connection = jdbcUtil1.getConnection();
        System.out.println(connection);

        String sql1 = "insert into t_emp(emp_name,emp_position,login_account,login_password) values(?,?,?,?)";
        String emp_name1 = "爱新烨";
        String emp_position = "dnf";
        String login_account = "xueye";
        String login_password = "pw";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);

        for (int i = 0; i < 1000; i++) {
            preparedStatement1.setString(1, emp_name1);// 这里注意从1
            preparedStatement1.setString(2, emp_position);
            preparedStatement1.setString(3, login_account + i);
            preparedStatement1.setString(4, login_password);
            preparedStatement1.addBatch();
            if ((i + 1) % 5 == 0) {
                preparedStatement1.executeBatch();
                preparedStatement1.clearBatch();  // 这批处理完成就clear掉
            }
        }
    }

}
