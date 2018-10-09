package com.greatdreams.learn.akka.streaming

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl.Source
import akka.stream.stage._

import scala.concurrent.duration.FiniteDuration

object StreamingTest4 {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("StreamingTest4")
    implicit val materializer = ActorMaterializer()

    val resultFuture = Source(1 to 6)
      .via(new Filter(_ % 2 == 0))
      .via(new Duplicator())
      .via(new Map(_ / 2))
      .runWith(new StdoutSink())

  }

}

class Map[A, B](f: A => B) extends GraphStage[FlowShape[A, B]] {
  val in = Inlet[A]("Map.in")
  val out = Outlet[B]("Map.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with StageLogging {
      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          log.info("Map.out")
          push(out, f(grab(in)))
        }
      } )
      setHandler(out, new OutHandler{
        override def onPull(): Unit = {
          log.info("Map.in")
          pull(in)
        }

      })
    }
}

class Filter[A](p: A => Boolean) extends GraphStage[FlowShape[A, A]] {
  val in = Inlet[A]("Filter.in")
  val out = Outlet[A]("Filter.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with StageLogging {
      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          log.info("Filter.in")
          val elem = grab(in)
          if(p(elem))
            push(out, elem)
          else
            pull(in)
        }
      } )
      setHandler(out, new OutHandler{
        override def onPull(): Unit = {
          log.info("filter.out")
          pull(in)
        }
      })
    }
}


class Duplicator[A] extends GraphStage[FlowShape[A, A]] {
  val in = Inlet[A]("Duplicator.in")
  val out = Outlet[A]("Duplicator.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with StageLogging {

      // Again: note that all mutable state
      // MUST be inside the GraphStageLogic

      var lastElem: Option[A] = None

      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          log.info("Duplicator.out")
          val elem = grab(in)
          lastElem = Some(elem)
          push(out, elem)
        }

        override def onUpstreamFinish(): Unit = {
          log.info("Duplicator.out")
          if (lastElem.isDefined)
            emit(out, lastElem.get)
          complete(out)
        }
      })


      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          if(lastElem.isDefined) {
            push(out, lastElem.get)
            lastElem = None
          }else {
            pull(in)
          }
        }
      })
    }
}


class TimeGate[A](silencePeriod: FiniteDuration) extends GraphStage[FlowShape[A, A]] {
  val in = Inlet[A]("TimerGate.in")
  val out = Outlet[A]("TimerGate.out")

  override val shape = FlowShape.of(in, out)

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new TimerGraphStageLogic(shape) with StageLogging {

      var open = false


      setHandler(in, new InHandler {
        override def onPush(): Unit = {
          log.info("TimerGate.out")
          val elem = grab(in)
          if(open) pull(in)
          else {
            push(out, elem)
            open = true
            scheduleOnce(None, silencePeriod)
          }
        }

        override def onUpstreamFinish(): Unit = {
          log.info("Duplicator.out")
          complete(out)
        }
      })


      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          pull(in)
        }
      })

      override protected def onTimer(timerKey: Any): Unit = {
        open = false
      }
    }
}
