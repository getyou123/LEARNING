package org.getyou123.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import java.util.Properties


//演示多种sparkSql的数据源，从不同的数据源来获取DF
object sparkSqlDataSource {

  def main(args: Array[String]): Unit = {

    /*
     获取SparkSession
     */
    val ss: SparkSession = SparkSession
      .builder()
      .appName("UDF")
      .master("local[2]")
      .getOrCreate()

    ss.sparkContext.setLogLevel("WARN")

    /**
     * 读取txt文本文件
     */
    val textRDD: RDD[String] = ss.sparkContext.textFile("spark_learn/src/main/resources/1.txt")
    import ss.implicits._
    textRDD.map(_.split(" "))
      .filter(_.length == 4)
      .map(x => (x(0), x(1), x(2), x(3)))
      .toDF("id", "name", "sex", "seat_num")
      .show()

    /**
     * 使用read.format.load 方式读取 txt文本文件
     */
    ss.read.format("text").load("spark_learn/src/main/resources/1.txt").show()

    /**
     * 读取单行的内json文件
     * 注意是单行内的
     */

    ss.read.format("json").load("spark_learn/src/main/resources/ex.json").schema.foreach(println(_))


    /**
     * 读取json文件可以进一步简写为：
     * ss.read.json("data/ex.json")
     */
    ss.read.json("spark_learn/src/main/resources/ex.json").foreach(println(_))


    /**
     * 直接去读
     * 读取parquet的文件的时候可以直接使用read.load,因为这个格式是默认的支持的数据格式不用再次指定format
     */

    /**
     * 读取hive表
     * 开启hive支持
     * enableHiveSupport() //使用hql的前提是开始hive支持，同时在spark_home的conf文件夹下可以到hive-site.xml
     * ss.sql("select * from emp").show()
     */

    /**
     * 连接读取mysql数据
     */
//    val properties = new Properties()
//    properties.setProperty("user", "root")
//    properties.setProperty("password", "123456")
//    val url = "jdbc:mysql://localhost:3306/ssm"
//    ss.read.jdbc(url, "t_user", properties)
//      .select("username", "sex", "email")
//      .where("id>=1")
//      //.show()
//
//    /**
//     * 写mysql数据
//     * 这里是读取然后按照Append的方式写回去
//     * 对于mysql的写的如果使用的是SaveMode.Overwrite 那么将会按照
//     * df的结构对mysql表进行重建然后写写入
//     */
//     .write.mode(SaveMode.Append).jdbc(url,
//      "t_user", properties)




    /**
     *  读取mongodb
     */
    val mongoDBDF: DataFrame = ss.read
      .format("com.mongodb.spark.sql.DefaultSource")
      .option("uri", "mongodb://root:123456@localhost/admin.user?authSource=admin")
      .load()
    mongoDBDF.show()

    /**
     *
     */


    ss.stop()


  }
}