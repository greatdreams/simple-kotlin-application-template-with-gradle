package com.greatdreams.learn.kotlin.coroutine

import kotlinx.coroutines.experimental.*

object MainProgram {
    @JvmStatic fun main(args: Array<String>) {
        launch {
            delay(2000L)
            println("coroutines!")
        }
        println("Hello, ")
        runBlocking {
            delay(3000L)
        }
    }
}