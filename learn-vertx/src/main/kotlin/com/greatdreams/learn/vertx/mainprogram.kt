package com.greatdreams.learn.vertx

import com.greatdreams.learn.vertx.websocket.WebsocketServerTest
import org.slf4j.LoggerFactory

object MainProgram {
    val logger = LoggerFactory.getLogger(MainProgram.javaClass)
    @JvmStatic fun main(args: Array<String>) {
        //  logger.info("Start HTTP Server ...")
        // HTTPServerTest.main(args)
        logger.info("Start Websocket Server ...")
        WebsocketServerTest.main(args)
    }
}