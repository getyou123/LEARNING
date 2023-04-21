package org.getyou123

import org.apache.flink.streaming.api.environment._
import org.slf4j.LoggerFactory

object WorldCountScalaJob {
  private val log = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream = env.socketTextStream("localhost", 7777)



    env.execute(getClass.getSimpleName)
  }

}
