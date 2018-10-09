package com.greatdreams.learn.akka.http.proxy

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, headers}
import akka.http.scaladsl.{ClientTransport, Http}
import akka.http.scaladsl.settings.{ClientConnectionSettings, ConnectionPoolSettings}
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ProxyTest {
  val logger = LoggerFactory.getLogger(ProxyTest.getClass)
  def main(args: Array[String]) = {
    implicit val system = ActorSystem()
    implicit val material = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionCOntext = system.dispatcher

    val proxyHost = "proxy1.asec.buptnsrc.com"
    val proxyPort = 8001
    // val proxyUsername = "proxy"
    // val proxyPassword = "123456"
    // instantiate the HTTP(S) proxy transport
    val proxyAddress = InetSocketAddress.createUnresolved(proxyHost, proxyPort)
    // val auth = headers.BasicHttpCredentials(proxyUsername, proxyPassword)
    // val val httpsProxyTransport = ClientTransport.httpsProxy(proxyAddress, auth)
    val httpsProxyTransport = ClientTransport.httpsProxy(proxyAddress)
    val settings = ConnectionPoolSettings(system)
      .withConnectionSettings(ClientConnectionSettings(system))
      .withTransport(httpsProxyTransport)

    //use http(S) proxy with HTTP().singleRequest
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "https://www.taobao.com"), settings = settings)
    responseFuture.onComplete {
      case Success(res) => logger.info(res.toString())
      case Failure(_) => logger.error("something wrong")
    }
    logger.info("Hello, akka http library")
  }
}
