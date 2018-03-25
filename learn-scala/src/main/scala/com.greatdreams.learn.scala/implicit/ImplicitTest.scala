package com.greatdreams.learn.scala.`implicit`

import org.slf4j.{Logger, LoggerFactory}

abstract class Monoid[A] {
  def add(x: A, y: A): A
  def unit: A
}

object ImplicitTest {
  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def add(x: String, y: String): String = x concat y
    def unit: String = ""
  }

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def add(x: Int, y: Int): Int = x + y
    def unit: Int = 0
  }

  def sum[A](xs: List[A])(implicit m: Monoid[A]): A =
    if (xs.isEmpty) m.unit
    else m.add(xs.head, sum(xs.tail))

  def main(args: Array[String]): Unit = {
    val logger: Logger = LoggerFactory.getLogger(ImplicitTest.getClass)
    logger.info(sum(List(1, 2, 3)).toString)       // uses IntMonoid implicitly
    logger.info(sum(List("a", "b", "c")).toString) // uses StringMonoid implicitly
  }
}
