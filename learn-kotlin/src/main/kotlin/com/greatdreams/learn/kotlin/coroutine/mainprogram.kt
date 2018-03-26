package com.greatdreams.learn.kotlin.coroutine

import com.greatdreams.learn.kotlin.with.WithTest
import kotlinx.coroutines.experimental.*

object MainProgram {
    @JvmStatic fun main(args: Array<String>) {
        WithTest.main(args)

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