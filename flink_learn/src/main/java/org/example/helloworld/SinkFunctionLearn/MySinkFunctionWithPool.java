package org.example.helloworld.SinkFunctionLearn;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.example.helloworld.pojo.WaterSensor;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用Druid作为连接池来管理和使用jdbc连接
 */
public class MySinkFunctionWithPool extends RichSinkFunction<WaterSensor> {
    private DruidDataSource dataSource;
    private Connection conn;

    @Override
    public void open(Configuration parameters) throws Exception {
        // 创建 Druid 连接池配置
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/flink_test?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        // 设置连接池大小
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        dataSource.setMaxWait(5000);
    }

    @Override
    public void close() throws Exception {
        // 释放资源
        if (dataSource != null) {
            dataSource.close();
        }
    }

    /**
     * 针对每个数据进行一次调用
     *
     * @param value   数据
     * @param context context
     */
    @Override
    public void invoke(WaterSensor value, Context context) throws Exception {
        try {
            conn = dataSource.getConnection();
//            conn.setAutoCommit(false);// 关闭自动连接
            PreparedStatement ps = conn.prepareStatement("insert into sensor values (?, ?, ?)");
            // 设置 PreparedStatement 参数并执行 SQL 语句
            ps.setString(1, value.getId());
            ps.setLong(2, value.getTs());
            ps.setInt(3, value.getVc());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            conn.close();
            e.printStackTrace();
        }
    }
}