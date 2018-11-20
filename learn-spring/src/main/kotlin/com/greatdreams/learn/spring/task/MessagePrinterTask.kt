package com.greatdreams.learn.spring.task

import org.slf4j.LoggerFactory
import java.util.*

class MessagePrinterTask(val message: String) : Runnable{
    companion object {
        val logger = LoggerFactory.getLogger(MessagePrinterTask::class.java)
    }
    override fun run() {
        Thread.sleep( Math.round(Random().nextDouble() * 10000))
        logger.info(message)
    }
}
