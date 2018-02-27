package com.greatdreams.learn.mybatis.plus

import org.slf4j.LoggerFactory

object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("application name is learn mybatis plus")
        log.info("The main program begins to run.")
        log.info("1 + 1= ${1 + 1}")
        log.info(System.getProperty("user.home"))
        log.info("The main program exits normally.")
    }
}