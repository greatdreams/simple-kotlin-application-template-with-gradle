package com.greatdreams.learn.akka.http

import com.greatdreams.learn.akka.http.actor.HTTPClientActorTest
import com.greatdreams.learn.akka.http.proxy.ProxyTest
import com.greatdreams.learn.akka.http.server.WebServer
import com.greatdreams.learn.akka.http.websocket.SingleWebSocketRequest
import org.slf4j.LoggerFactory

object MainProgram {
  val logger = LoggerFactory.getLogger(MainProgram.getClass)
  def main(args: Array[String]) = {
    logger.info("Hello, akka http library")
    // ProxyTest.main(args)
    // SingleWebSocketRequest.main(args)
    // HTTPClientActorTest.main(args)
    WebServer.main(args);
  }
}
