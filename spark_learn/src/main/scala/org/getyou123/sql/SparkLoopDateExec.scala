package org.getyou123.sql

/**
 * 实现spark-shell中，按照日期循环进行代码函数调用
 */

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SparkLoopDateExec {

  /**
   * 需要执行的函数
   *
   * @param event_day
   */
  def execFunction(event_day: String): Unit = {
    println("execFunction:" + event_day)
  }

  def main(args: Array[String]): Unit = {

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val startDate = LocalDate.of(2024, 1, 31) // 开始日期 包含当天
    val endDate = LocalDate.of(2024, 2, 28) // 结束日期 包含当天

    // 从前往后执行，串行
    for (date <- Iterator.iterate(startDate)(_ plusDays 1).takeWhile(!_.isAfter(endDate))) {
      println(date.format(formatter))
      val event_day = date.format(formatter)
      execFunction(event_day)
    }


    // 从后往前
    for (date <- Iterator.iterate(endDate)(_ minusDays 1).takeWhile(!_.isBefore(startDate))) {
      println(date.format(formatter))
      val event_day = date.format(formatter)
      execFunction(event_day)
    }
  }

}
