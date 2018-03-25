package com.greatdreams.learn.scala

import com.greatdreams.learn.scala.`implicit`.{ImplicitClassTest, ImplicitTest}
import org.slf4j.{Logger, LoggerFactory}

object MainProgram {
  val logger: Logger = LoggerFactory.getLogger(MainProgram.getClass)
  def main(args: Array[String]):Unit = {
    logger.info("Hello, scala")

    ImplicitTest.main(args)
    ImplicitClassTest.main(args)
  }
}