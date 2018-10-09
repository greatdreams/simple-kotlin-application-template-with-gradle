package com.greatdreams.learn.akka.http.actor

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.ActorMaterializer
import akka.util.ByteString

class HTTPClientActor extends Actor with ActorLogging {

  import akka.pattern.pipe
  import context.dispatcher

  final implicit val materializer: ActorMaterializer = ActorMaterializer()

  val http = Http(context.system)

  override def preStart(): Unit = {
    http.singleRequest(HttpRequest(uri = "http://www.xju.edu.cn")).pipeTo(self)
  }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) => {
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach {
        body =>
          log.info("Got response, body: " + body.utf8String)
      }
    }
    case resp@HttpResponse(code, _, _, _) => {
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
    }
  }
}
