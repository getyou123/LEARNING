package org.getyou123.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object KeyValue01_partitionBy {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3具体业务逻辑
    //3.1 创建第一个RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "aaa"), (2, "bbb"), (3, "ccc")), 3)

    //3.2 自定义分区
    val rdd3: RDD[(Int, String)] = rdd.partitionBy(new MyPartitioner(2))

    //4 打印查看对应分区数据
    rdd3.mapPartitionsWithIndex((index, list) => list.map((index, _)))
      .collect().foreach(println)

    //5.关闭连接
    sc.stop()
  }

  // 自定义分区

  class MyPartitioner(num: Int) extends Partitioner {

    // 设置的分区数
    override def numPartitions: Int = num

    // 具体分区逻辑
    // 根据传入数据的key   输出目标的分区号
    // spark中能否根据value进行分区   =>   不能  只能根据key进行分区
    override def getPartition(key: Any): Int = {
      // 使用模式匹配  对类型进行推断
      // 如果是字符串  放入到0号分区  如果是整数  取模分区个数
      key match {
        case s: String => 0
        case i: Int => i % numPartitions
        case _ => 0
      }
    }

  }

}
