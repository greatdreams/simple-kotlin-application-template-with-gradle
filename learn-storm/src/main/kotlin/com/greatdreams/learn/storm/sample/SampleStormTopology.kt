package com.greatdreams.learn.storm.sample

import org.apache.storm.Config
import org.apache.storm.LocalCluster
import org.apache.storm.topology.TopologyBuilder

fun main() {
    // create an instance of TopologyBuilder class
    val builder = TopologyBuilder()
    // set the spout class
    builder.setSpout("SampleSpout", SampleSpout(), 2)
    // set the bolt class
    builder.setBolt("SampleBolt", SampleBolt(), 4)
            .shuffleGrouping("SampleSpout")
    val conf = Config()
    conf.setDebug(true)
    conf.setNumWorkers(10)
    // create an instance of LocalCluster class for executing topology in local mode.
    val cluster = LocalCluster()
    // SampleStormTopology is the name of submitted topology
    cluster.submitTopology("SampleStormTopology", conf, builder.createTopology())
    try {
        Thread.sleep(100 * 10000)
    }catch (e: Exception) {
        e.printStackTrace()
    }

    // kill the SampleStormTopology
    cluster.killTopology("SampleStormTopology")
    // shutdown the storm test cluster
    cluster.shutdown()
}