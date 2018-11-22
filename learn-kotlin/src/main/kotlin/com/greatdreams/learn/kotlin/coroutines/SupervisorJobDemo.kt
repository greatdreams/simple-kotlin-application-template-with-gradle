package com.greatdreams.learn.kotlin.coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        val firstChild = launch(CoroutineExceptionHandler { ctx, throwable ->
            throwable.printStackTrace()
        }) {
            println("First child is failing")
            throw AssertionError("First child is cancelled")
        }

        val secondChild = launch {
            firstChild.join()
            println("First child is cancelled:${firstChild.isCancelled}, but second one is still active")
            try {
                delay(Long.MAX_VALUE)
            }finally {
                // But cancellation of the supervisor is propagated
                println("Second child is cancelled because supervisor is cancelled.")
            }
        }
        // wait until the first child fails & completes
        firstChild.join()
        println("Cancelling supervisor")
        supervisor.cancel()
        secondChild.join()

    }
}