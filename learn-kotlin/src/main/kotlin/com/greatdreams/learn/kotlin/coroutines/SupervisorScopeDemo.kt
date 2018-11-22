package com.greatdreams.learn.kotlin.coroutines

import kotlinx.coroutines.*
import java.lang.AssertionError

/*
fun main() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                }finally {
                    println("Child is cancelling")
                }
            }

            //Give our child a chance to execute and print using yield
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }

    }catch (e: AssertionError) {
        println("Caught assertion error")
    }
}
*/

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler {
        _, exception ->
        println("Caught $exception")
    }

    supervisorScope {
        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }

    println("Scope is completed")
}