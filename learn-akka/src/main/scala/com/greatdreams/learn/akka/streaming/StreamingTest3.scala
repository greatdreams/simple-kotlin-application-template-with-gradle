package com.greatdreams.learn.akka.streaming

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream._
import akka.stream.stage._

import scala.concurrent.Future

object StreamingTest3 {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("streaming")
    implicit val materializer = ActorMaterializer()
    import scala.concurrent.ExecutionContext.Implicits.global


    // A GraphStage is a proper Graph, just like what GraphDSL.create wourld
    // return
    val sourceGraph: Graph[SourceShape[Int], NotUsed] = new NumbersSource
    // create a Source from the Graph to access the DSL
    val mySource: Source[Int, NotUsed] = Source.fromGraph(sourceGraph)
    // return 55
    val result1: Future[Int] = mySource.take(10).runFold(0)(_ + _)
    // the source is reusable. this return 5050
    val result2: Future[Int] = mySource.take(100).runFold(0)(_ + _)

    result1.onComplete ( value =>
      println(value)
    )
    result2.onComplete(value =>
      println(value)
    )

  }
}

class NumbersSource extends GraphStage[SourceShape[Int]] {
  // Define the(sole) output port of this stage
  val out: Outlet[Int] = Outlet("NumbersSource")
  // Define the shape of this stage which is SourceShape with the port we defined

  override def shape: SourceShape[Int] = SourceShape(out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape) {
    private var counter = 1
    setHandler(out, new OutHandler {
      override def onPull(): Unit = {
        push(out, counter)
        counter += 1
      }
    })
  }
}

class StdoutSink extends GraphStage[SinkShape[Int]] {
  val in: Inlet[Int] = Inlet("StdoutSink")

  override val shape: SinkShape[Int] = SinkShape(in)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with StageLogging {
      // This requests onee element at the Sink startup
      override def preStart(): Unit = pull(in)
      setHandler(in, new InHandler() {
        override def onPush(): Unit = {
          log.info("StdoutSink.out")
          println(grab(in))
          pull(in)
        }
      })
    }
}