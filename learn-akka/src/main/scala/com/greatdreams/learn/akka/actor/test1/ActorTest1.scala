package com.greatdreams.learn.akka.actor.test1

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

object ActorTest1 {
  val logger = LoggerFactory.getLogger(ActorTest1.getClass)
  def main(args: Array[String]): Unit = {
    println(ActorSystem.Version)
    val system = ActorSystem("TestSystem")
    val actor = system.actorOf(Props[MyActor], "myactor")
    logger.info(actor.path.toStringWithoutAddress)
    actor ! "test"
    actor ! "default message"
  }
}
