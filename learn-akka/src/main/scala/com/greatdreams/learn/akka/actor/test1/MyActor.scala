package com.greatdreams.learn.akka.actor.test1

import akka.actor.Actor
import akka.event.Logging

class MyActor extends Actor{
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case "test" =>
      log.info("This is a test information")
    case _ =>
      log.info("This is a default message handler")
  }
}
