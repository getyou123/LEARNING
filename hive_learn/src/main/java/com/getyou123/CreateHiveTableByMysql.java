package com.getyou123;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.sql.*;
import java.util.Arrays;


public class CreateHiveTableByMysql {
    /**
     * 定义需要解析的参数形式
     */
    static class CreateHiveTableByMysqlAppArgs {
        @Parameter(names = {"-h", "--help"}, description = "Print help message")
        private boolean help = false;

        @Parameter(names = {"-dbUrl"}, description = "数据库链接url", required = true)

        private String dbUrl;
        @Parameter(names = {"-userName"}, description = "用户名", required = true)
        private String userName;

        @Parameter(names = {"-password"}, description = "密码", required = true)
        private String password;

        @Parameter(names = {"-db", "-dbName", "--databaseName"}, description = "数据库名", required = true)
        private String dbName;

        @Parameter(names = {"-tb", "-tbName", "--tableName"}, description = "表名", required = true)
        private String tbName;
        @Parameter(description = "传入参数错误")
        private String mainParameter;
    }

    /**
     * 获取mysql连接
     *
     * @param url      url
     * @param user     user
     * @param password password
     */
    public static Connection getConnection(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照mysql的列类型进行映射到hive的type上
     *
     * @param columnType 列类型
     */
    private static String getHiveDataType(String columnType) {
        if (Arrays.asList("varchar", "text", "blob", "char", "json",
                "longblob", "longtext", "mediumblob", "mediumtext",
                "enum", "set", "datetime", "timestamp", "time",
                "date", "year").contains(columnType.toLowerCase())) {
            return "STRING";
        } else if (columnType.equalsIgnoreCase("bigint") || columnType.toLowerCase().startsWith("bigint")) {
            return "BIGINT";
        } else if (Arrays.asList("int", "smallint", "tinyint", "mediumint", "integer").contains(columnType.toLowerCase()) || columnType.toLowerCase().startsWith("int")) {
            return "INT";
        } else if (Arrays.asList("double", "float").contains(columnType.toLowerCase())) {
            return "DOUBLE";
        } else if (columnType.equalsIgnoreCase("decimal")) {
            return columnType;
        } else {
            return "STRING";
        }
    }

    public static void main(String[] args) throws SQLException {
        CreateHiveTableByMysqlAppArgs myAppArgs = new CreateHiveTableByMysqlAppArgs();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(myAppArgs)
                .build();
        jCommander.parse(args);

        if (myAppArgs.help) {
            jCommander.usage();
            return;
        }

        String url = "jdbc:mysql://" + myAppArgs.dbUrl + ":3306/" + myAppArgs.dbName;
        Connection connection = getConnection(url, myAppArgs.userName, myAppArgs.password);

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet columns = metaData.getColumns(null, null, myAppArgs.tbName, null);

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append("ods").append(".").append("hiveTable").append(" (");
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnComment = columns.getString("REMARKS");
            String columnType = columns.getString("TYPE_NAME");
            sb.append(columnName).append(" ")
                    .append(getHiveDataType(columnType))
                    .append(" COMMENT \"").append(columnComment).append("\", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");

        System.out.println(sb.toString());

        connection.close();

    }

}
