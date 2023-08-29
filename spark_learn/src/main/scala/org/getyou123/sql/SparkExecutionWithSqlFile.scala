package org.getyou123.sql

import scala.io.Source


/**
 * @description: 实现按照sql文件执行具体的sql文件
 * @author: haoguowang
 * @date: 2022/08/16
 */
object SparkExecutionWithSqlFile {

  /**
   * sql中变量的替换和使用
   *
   * @param sql
   * @param params
   * @return
   */
  def replaceVariables(sql: String, params: Map[String, String]): String = {
    params.foldLeft(sql) {
      (sql, param) => sql.replaceAll("\\$\\{" + param._1 + "\\}", param._2)
    }
  }


  /**
   * 实现sql文件内容的读取
   *
   * @param filename
   * @return
   */
  def readSQLFile(filename: String): String = {
    val source = Source.fromFile(filename)
    val sql = try source.mkString finally source.close()
    sql
  }


  /**
   * 主要实现读取sql文件然后替换sql变量执行sql
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {

    val fileSql = args(0) // 需要读取的sql文件
    val sql = readSQLFile(fileSql)

    /**
     * 变量替换的字典
     */
    val event_day = "23023001"
    val id = "id"
    val params = Map("event_day" -> event_day, "field_name" -> id)


    // 替换sql中的变量
    val formattedSQL = replaceVariables(sql, params)
    println("替换sql中的变量之后的sql：".concat(formattedSQL))

  }

}
