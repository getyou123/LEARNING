### 使用spark-sql写mysql数据
```scala
    // 获取 spark_session
    val spark_session_cluster = get_spark_session_cluster
    // 获取参数
    val sql =
      """
        |
        |""".stripMargin

    val frame = spark_session_cluster.sql(sql)
    val prop = new java.util.Properties
    prop.setProperty("user", "XX")
    prop.setProperty("password", "XX")
    frame.write.mode(SaveMode.Append).jdbc("jdbc:mysql://XX..com:3306/dynamic",
      "user_monitor_XXX", prop)
  }
```
在提交时候需要同时指明--jars和 --driver-class-path
```shell
spark-submit  \
--class  com.bigdata.patent.TestDevUse \
--master yarn  \
--deploy-mode  cluster  \
--executor-memory  4g  \
--num-executors  2 \
--executor-cores 1 \
--driver-memory  4g  \
--driver-cores 1 \
--proxy-user XXXX \
--queue   offline   \
--name getyou123 \
--driver-class-path hdfs:///user/XXX/jars/mysql-connector-java-5.1.48.jar \
--jars  hdfs:///user/haoguowang/jars/mysql-connector-java-5.1.48.jar \
hdfs:///user/haoguowang/jars/TestDevTableToDev-jar-with-dependencies.jar
```
否则报错：
``` No suitable driver ```
