package com.greatdreams.learn.netty

import com.greatdreams.learn.netty.server.DiscardServer
import org.slf4j.LoggerFactory

/**
 * main entry to program for learning netty
 * @author greatdreams(wanghuaweijoy@foxmail.com)
 *
 */

object MainProgram {
    val logger = LoggerFactory.getLogger(MainProgram.javaClass)
    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("begins to netty application.")
        DiscardServer.main(args)
        logger.info("netty application exit.")
    }
}