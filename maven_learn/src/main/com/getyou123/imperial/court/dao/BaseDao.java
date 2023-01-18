package com.getyou123.imperial.court.dao;

import com.getyou123.imperial.court.util.JdbcUtilImperialCourt;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

/**
 * 这个是对于数据库进行操作的各种的抽象的操作的定义
 *
 * @param <T> 这个具体的实体的类的抽象
 */
public class BaseDao<T> {

    /**
     * 用于实现查询的QueryRunner
     */
    private QueryRunner runner = new QueryRunner();

    /**
     * 这里的返回值的类型是T，就是查询数据库的返回的数据抽象类
     *
     * @param sql
     * @param parameters
     * @return 返回数据查询的类型
     */
    public T getSingleBean(String sql, Class<T> entityClass, Object... parameters) {
        try {
            Connection connection = JdbcUtilImperialCourt.getConnection();
            return runner.query(connection, sql, new BeanHandler<>(entityClass), parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询多个对象返回对象的list
     */

    public List<T> getBeanList(String sql, Class<T> entityClass, Object... parameters) {
        try {
            Connection connection = JdbcUtilImperialCourt.getConnection();
            return runner.query(connection, sql, new BeanListHandler<>(entityClass), parameters);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 这个是通用的增删改查操作,返回操作的行数
     *
     * @param sql
     * @param parameters
     * @return
     */
    public int update(String sql, Object... parameters) {
        try {
            Connection connection = JdbcUtilImperialCourt.getConnection();
            int update = runner.update(connection, sql, parameters);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
