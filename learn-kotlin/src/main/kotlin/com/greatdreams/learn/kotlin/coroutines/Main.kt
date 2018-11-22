package com.greatdreams.learn.kotlin.coroutines

import kotlinx.coroutines.*
import java.lang.ArithmeticException

/*
fun main() = runBlocking {
    repeat(100_00) { i ->
        launch(Dispatchers.Default) {
            delay(1000L)
            println("${Thread.currentThread().name} I'm sleeping $i ...")
        }
    }
}
*/

/*
fun main() = runBlocking {
    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException()
    }
    job.join()
    println("Joined failed job")

    val defered = GlobalScope.async {
        println("Throing exception from async")
        throw ArithmeticException()
    }

    /*
    try {
        defered.await()
    }catch (e: java.lang.ArithmeticException) {
        println("Caught ArithmetircException")
    }
    */
    defered.await()
    println("application exist")

}
*/

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler {ctx, exception ->
        println("Caught $exception")
    }

    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }

    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException()
    }

    joinAll(job, deferred)

}