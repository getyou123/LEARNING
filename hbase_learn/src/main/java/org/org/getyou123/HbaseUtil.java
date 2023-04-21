package org.org.getyou123;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import org.apache.hadoop.hbase.client.Get;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseUtil {
    private static final Logger log = LoggerFactory.getLogger(HbaseUtil.class);
    private static Configuration conf = null;
    private static Connection conn = null;

    public HbaseUtil(String zkUrl, String zkPort) {
        this.conf = HBaseConfiguration.create();
        this.conf.set("hbase.zookeeper.quorum", zkUrl + ":" + zkPort);
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断给定表是否存在
     */
    public static boolean tableExists(String tableName) throws IOException {
        Admin admin = conn.getAdmin();
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        admin.close();
        return exists;
    }

    /**
     * 删除表
     */
    public static void deleteTable(String tableName) throws IOException {
        Admin admin = conn.getAdmin();
        admin.disableTable(TableName.valueOf(tableName));
        admin.deleteTable(TableName.valueOf(tableName));
        admin.close();
        System.out.println("Table deleted: " + tableName);
    }

    /**
     * 新建表
     */
    public static void createTable(String tableName, String[] colFamily) throws IOException {
        Admin admin = conn.getAdmin();

        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println("Table already exists: " + tableName);
            return;
        }

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (int i = 0; i < colFamily.length; i++) {
            tableDescriptor.addFamily(new HColumnDescriptor(colFamily[i]));
        }

        admin.createTable(tableDescriptor);
        System.out.println("Table created: " + tableName);
        admin.close();
    }

    /**
     * 按照表名和 Rowkey 读取数据
     */
    public static void getARecordByRowKey(String tableName, String rowKey) throws IOException {

        Table table = conn.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);

        for (Cell cell : result.listCells()) {
            byte[] family = CellUtil.cloneFamily(cell);
            byte[] qualifier = CellUtil.cloneQualifier(cell);
            byte[] value = CellUtil.cloneValue(cell);

            System.out.println(
                    Bytes.toString(family) + ":" + Bytes.toString(qualifier) + " = " + Bytes.toString(value));
        }
        table.close();
    }

    public static void main(String[] args) throws IOException {


        System.out.println("==============");
        HbaseUtil hbaseUtil = new HbaseUtil("10.XX.1.XXX", "2181");
        hbaseUtil.getARecordByRowKey("test:intellectual_property_splice", new StringBuilder("1234567").reverse().toString());


//        System.out.println("==============");
//        hbaseUtil.getARecordByRowKey("test:intellectual_property_splice", new StringBuilder("2806765").reverse().toString());
    }
}
