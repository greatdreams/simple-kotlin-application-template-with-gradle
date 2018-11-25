package com.greatdreams.learn.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  def main(args: Array[String]): Unit = {

    println("learn spark")
    val conf = new SparkConf()
    conf.setMaster("local[4]")
      .setAppName("test")
      .set("spark.cores.max", "10")
    /*
    val spark = new SparkContext(conf)
    println(spark.master)
    spark.jars.foreach(jar =>
      println(jar)
    )
    Thread.sleep(1000 * 1000)
    */
    val spark = SparkSession.builder().appName("test")
        .config(conf)
        .getOrCreate()

    val data = spark.read.format("csv")
      .option("sep", "\t")
      .option("inferSchema", "true")
      .option("header", "true")
      .load("dataset/2013-08-20_capture-win2.netflow.csv")
    println(data.first()(0))
    data.foreach {
      row =>
        println(row(0))
    }

    // print information
    val desc = data.describe("StartTime", "Dur", "Proto", "SrcAddr")
    desc.foreach {
      row => {
        row.toSeq.foreach {
          value =>
            print(s"${value},")
        }
        print("\n")
      }
    }
    // val startCol = data("StartTime")
    // startCol.

    Thread.sleep(1000 * 1000)
    spark.close()
  }
}
