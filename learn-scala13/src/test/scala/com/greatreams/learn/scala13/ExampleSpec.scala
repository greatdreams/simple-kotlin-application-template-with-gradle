package com.greatreams.learn.scala13

import java.util

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

class ExampleSpec extends FlatSpec with Matchers{


  "the length of the list " should " should be 3" in {
    val names = new util.LinkedList[String]()

    names.add("A")
    names.add("B")
    names.add("C")

    names.size() should be (3)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val stack = new mutable.ArrayStack[String]
    stack.pop()
  }

}