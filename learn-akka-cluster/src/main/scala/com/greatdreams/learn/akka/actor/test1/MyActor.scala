package com.greatdreams.learn.akka.actor.test1

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
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

class SimpleClusterListener extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) ⇒
      log.info("Member is Up: {}", member.address)
    case UnreachableMember(member) ⇒
      log.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) ⇒
      log.info(
        "Member is Removed: {} after {}",
        member.address, previousStatus)
    case _: MemberEvent ⇒ // ignore
  }
}