package com.greatdreams.kotlin.example.rxkotlin

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import org.slf4j.LoggerFactory

object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("The main program begins to run.")

        val list = listOf("Aplpha", "Beta", "Gamma", "Delta", "Epsilon")
        list.toObservable()
                .filter { it.length > 5 }
                .subscribeBy(
                        onNext = { println(it) },
                        onError = { it.printStackTrace() },
                        onComplete = { println("Done!") }
                )

        log.info("The main program exits normally.")
    }
}