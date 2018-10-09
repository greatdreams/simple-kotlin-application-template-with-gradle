package com.greatdreams.learn.akka.streaming

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import org.slf4j.LoggerFactory

import scala.concurrent.Future

object StreamingTest2 {
  val logger = LoggerFactory.getLogger(StreamingTest2.getClass)
  def main(args: Array[String]) = {
    logger.info("Hello, akka")

    implicit val system = ActorSystem("QuickStart")
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 100)
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

    val result: Future[IOResult] =
      factorials
        .map(num ⇒ ByteString(s"$num\n"))
        .runWith(FileIO.toPath(Paths.get("factorials.txt")))

    implicit val ec = system.dispatcher
    result.onComplete(_ ⇒ system.terminate())
  }
}