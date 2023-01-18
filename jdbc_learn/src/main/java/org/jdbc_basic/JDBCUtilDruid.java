package org.jdbc_basic;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 这里封装一个连接池工具druid，管理数据库的连接，
 * 主要包括获取，使用，返回pool等
 */
public class JDBCUtilDruid {
    private static DataSource ds;
    //静态代码块加载配置文件
    static{

        try {
            //加载配置文件
            Properties pro=new Properties();
            //使用ClassLoader加载配置文件，获取字节输入流
            InputStream is = JDBCUtilDruid.class.getClassLoader().getResourceAsStream("jdbc.properties");
            pro.load(is);
            //初始化连接池对象,创建数据库连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接池对象
     */
    public static DataSource getDataSource(){
        return ds;
    }

    /**
     * 获取Connection对象
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 关闭（归还资源）
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if (rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt!=null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  static void close(Statement stmt,Connection conn){
        close(null,stmt,conn);
    }
}
