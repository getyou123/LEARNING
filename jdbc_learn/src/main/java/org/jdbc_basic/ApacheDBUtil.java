package org.jdbc_basic;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 通过工具类来操作封装为list
 */
public class ApacheDBUtil {

    public static void main(String[] args) throws SQLException {
        // 通过一个druid获取一个连接
        Connection connection = JDBCUtilDruid.getConnection();

        /**
         * 多行分装为多个bean
         */
        QueryRunner queryRunner = new QueryRunner();
        String Sql = "select emp_id, emp_name, emp_position, login_account from t_emp where emp_name = ? ";
        String emp_name = "爱新烨";
        List<t_emp> query_results = queryRunner.query(connection, Sql, new BeanListHandler<>(t_emp.class), emp_name);
        for (t_emp t : query_results) {
            System.out.println(t);
        }

        /**
         * 单行的记录
         */
        String Sql1 = "select emp_id, emp_name, emp_position, login_account from t_emp where emp_id = ? ";
        int emp_id = 1;
        t_emp query_result = queryRunner.query(connection, Sql1, new BeanHandler<>(t_emp.class), emp_id);
        System.out.println(query_result);

        /**
         * 返回单行单列的对象
         */
        String Sql2 = "select login_account from t_emp where emp_id = ? ";
        Object res = queryRunner.query(connection, Sql2, new ScalarHandler<>(), emp_id);
        System.out.println(res);

        /**
         * 完成DML insert update delete
         */
        String Sql3 = "update t_emp set login_account = ? where emp_id = ? ";
        String login_account = "xiaoxuanzi16545";
        int updateRows = queryRunner.update(connection, Sql3, login_account, emp_id);
        System.out.println(updateRows);

        connection.close(); // 关闭连接,这里也是只需要关闭connection，query底层已经关闭了results和prepareStatement

    }

}
