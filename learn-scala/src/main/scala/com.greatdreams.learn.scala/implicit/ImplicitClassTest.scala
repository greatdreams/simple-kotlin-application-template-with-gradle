package com.greatdreams.learn.scala.`implicit`

import org.slf4j.{Logger, LoggerFactory}

object HelperObject {
  implicit class IntWithTimes(x: Int) {
    def times[A](f: => A): Unit = {
      def loop(current: Int): Unit =
        if(current > 0) {
          f
          loop(current - 1)
        }
      loop(x)
    }
  }
}
object ImplicitClassTest {
  def main(args: Array[String]): Unit = {
    val logger: Logger = LoggerFactory.getLogger(ImplicitClassTest.getClass)

    import HelperObject._

    5 times logger.info("Hello, scala")
  }
}
