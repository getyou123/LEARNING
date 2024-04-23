package org.example.flink_training.SinkFunctionLearn;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.example.flink_training.pojo.WaterSensor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 * 自定义的sink方法
 * 因为是Function所以是每个subtask实例构造一个Function实例
 * open方法只在subtask实例上执行一次
 * 本示例中的sinker不是可以回放的
 */
public class MySinkFunction extends RichSinkFunction<WaterSensor> {

    private PreparedStatement ps;
    private Connection conn;

    @Override
    public void open(Configuration parameters) throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flink_test?useSSL=false", "root", "123456");
        ps = conn.prepareStatement("insert into sensor values(?, ?, ?)");

    }

    @Override
    public void close() throws Exception {
        ps.close();
        conn.close();
    }

    // 对于每条数据执行这个函数
    @Override
    public void invoke(WaterSensor value, Context context) throws Exception {
        ps.setString(1, value.getId());
        ps.setLong(2, value.getTs());
        ps.setInt(3, value.getVc());
        ps.execute();
    }

}
