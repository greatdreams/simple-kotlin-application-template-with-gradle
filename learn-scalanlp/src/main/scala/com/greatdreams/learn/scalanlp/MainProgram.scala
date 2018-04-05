package com.greatdreams.learn.scalanlp
import breeze.linalg.DenseVector

object MainProgram {
  def main(args: Array[String]): Unit = {
    println("Hello, scalanlp!")
    val x = DenseVector.zeros[Double](5)
    println(x)
  }
}
