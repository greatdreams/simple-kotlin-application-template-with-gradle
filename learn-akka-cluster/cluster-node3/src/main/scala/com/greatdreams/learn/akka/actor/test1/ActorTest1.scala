package com.greatdreams.learn.akka.actor.test1

import akka.actor.{ActorSystem, Props}
import org.slf4j.LoggerFactory

object ActorTest1 {
  val logger = LoggerFactory.getLogger(ActorTest1.getClass)
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("clustersystem")
    val actor = system.actorOf(Props[MyActor], "myactor")
    logger.info(actor.path.toStringWithoutAddress)

    val actor1 = system.actorOf(Props[SimpleClusterListener], "simpleClusterActor")
    logger.info(actor1.path.toStringWithoutAddress)

    actor ! "test"
    actor ! "default message"
  }
}
