package com.greatdreams.learn.akka

import com.greatdreams.learn.akka.actor.test1.ActorTest1
import com.greatdreams.learn.akka.streaming.{StreamingTest3, StreamingTest4}
import org.slf4j.LoggerFactory

object MainProgram {
  val logger = LoggerFactory.getLogger(MainProgram.getClass)
  def main(args: Array[String]) = {
    logger.info("Hello, akka")
    // StreamingTest1.main(args)
    // StreamingTest2.main(args)
    // StreamingTest3.main(args)
    StreamingTest4.main(args)
    ActorTest1.main(args)
  }
}
