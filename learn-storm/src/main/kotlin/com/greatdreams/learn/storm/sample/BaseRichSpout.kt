package com.greatdreams.learn.storm.sample

import org.apache.storm.spout.SpoutOutputCollector
import org.apache.storm.task.TopologyContext
import org.apache.storm.topology.BasicOutputCollector
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseBasicBolt
import org.apache.storm.topology.base.BaseRichSpout
import org.apache.storm.tuple.Fields
import org.apache.storm.tuple.Tuple
import org.apache.storm.tuple.Values
import java.util.*

class SampleSpout: BaseRichSpout() {
    companion object {
        @JvmStatic val serialVersionUID: Long = 1L
        @JvmStatic val map: HashMap<Int, String> by lazy {
            val map1 = HashMap<Int, String>()
            map1.put(0, "google")
            map1.put(1, "facebook")
            map1.put(2, "twitter")
            map1.put(3, "youtube")
            map1.put(4, "linkedin")
            map1
        }
    }
    var spoutOutputCollector: SpoutOutputCollector? = null

    override fun nextTuple() {
        // Storm cluster repeatedly calls this method to emit a continous stream of tuples
        val rand = Random()
        // generate the random number from 0 to 4.
        val randomNumber = rand.nextInt(5)
        spoutOutputCollector?.emit(Values(map.get(randomNumber)))

        try {
            Thread.sleep(5000)
        }catch(e: Exception) {
            e.printStackTrace()
        }
    }

    // Open the spout
    override fun open(conf: MutableMap<Any?, Any?>?, context: TopologyContext?, collector: SpoutOutputCollector?) {
        this.spoutOutputCollector = collector
    }

    override fun declareOutputFields(declarer: OutputFieldsDeclarer?) {
        declarer?.declare(Fields("site"))
    }
}

class SampleBolt: BaseBasicBolt() {
    companion object {
        val serialVersionUID = 1L
    }
    override fun execute(input: Tuple?, collector: BasicOutputCollector?) {
        // fetched the field "site" from input tuple
        val test = input?.getStringByField("site")
        // print the value of field "site" on console
        println("Name of input site is : $test")
    }
    override fun declareOutputFields(declarer: OutputFieldsDeclarer?) {
    }
}

