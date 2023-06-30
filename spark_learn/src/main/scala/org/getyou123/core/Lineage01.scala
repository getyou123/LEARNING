package org.getyou123.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Lineage01 {

  def main(args: Array[String]): Unit = {

    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val fileRDD: RDD[String] = sc.textFile("/Users/haoguowang/IdeaProjects/LEARNING/Book_Note/inbox/路线.md")
    println(fileRDD.toDebugString)
    // (2) /Users/haoguowang/IdeaProjects/LEARNING/Book_Note/inbox/路线.md MapPartitionsRDD[1] at textFile at Lineage01.scala:16 []
    // |  /Users/haoguowang/IdeaProjects/LEARNING/Book_Note/inbox/路线.md HadoopRDD[0] at textFile at Lineage01.scala:16 []
    // 注意：圆括号中的数字表示RDD的并行度，也就是有几个分区
    println("----------------------")

    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    println(wordRDD.toDebugString)
    println("----------------------")

    val mapRDD: RDD[(String, Int)] = wordRDD.map((_,1))
    println(mapRDD.toDebugString)
    println("----------------------")

    val resultRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_+_)
    println(resultRDD.toDebugString)

    resultRDD.collect()

    //4.关闭连接
    sc.stop()
  }
}
