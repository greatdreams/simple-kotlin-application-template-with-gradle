package com.greatdreams.kotlin.template

import org.slf4j.LoggerFactory

/**
 * @author Greatdreams
 * Created by greatdreams on 1/9/17.
 * Use [kotlin.reflect.KClass.declaredMemberExtensionProperties] to enumerate
 */
object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("The main program begins to run.")
        log.info("The main program exits normally.")
    }
}