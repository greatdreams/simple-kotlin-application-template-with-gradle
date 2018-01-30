package com.greatdreams.kotlin.example.functional

import org.slf4j.LoggerFactory

object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("The main program begins to run.")



        log.info("The main program exits normally.")
    }
}