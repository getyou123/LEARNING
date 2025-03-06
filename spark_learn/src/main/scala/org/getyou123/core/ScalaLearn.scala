package org.getyou123.core

object ScalaLearn {
  /**
   * 对输入的参数作必要的限制并给出提示
   *
   * @param in
   */
  def fun1(in: String): Unit = {
    assert(in.length >= 10, s"input string must have length greater than 10")
    val unit = in.toLowerCase()
    println(unit)
  }
}
