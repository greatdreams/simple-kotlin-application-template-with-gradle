package com.greatdreams.learn.akka.http.actor

import akka.actor.{ActorSystem, Props}

object HTTPClientActorTest {
  def main(args: Array[String]) = {
    val system = ActorSystem("http_client_test")
    val prop = Props[ HTTPClientActor]
    val actor = system.actorOf(prop, "httpClientActor")
  }
}
