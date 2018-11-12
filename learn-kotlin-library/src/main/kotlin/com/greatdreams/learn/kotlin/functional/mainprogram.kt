package com.greatdreams.learn.kotlin.functional

import com.greatdreams.learn.kotlin.functional.coroutines.*
import org.slf4j.LoggerFactory

object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("The main program begins to run.")
        // HelloCoroutine.main(args)
        // HelloCoroutine1.main(args)
        // HelloCoroutine2.main(args)
        // HelloCoroutine3.main(args)
        // HelloCoroutine4.main(args)
        // HelloCoroutine5.main(args)
        // HelloCoroutine6.main(args)
        HelloCoroutine7.main(args)
        log.info("The main program exits normally.")
    }
}