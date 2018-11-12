package com.greatdreams.learn.kotlin.functional.coroutines

import kotlinx.coroutines.*
import java.util.*

object HelloCoroutine {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        launch {
            delay(10000L)
            println("Hello kotlinx.coroutine")
        }
        println("Hello, kotlin")
        Thread.sleep(20000L)
    }
}


object HelloCoroutine1 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        val job = launch { doword() }
        print("Hello")
        job.join()
    }

    suspend fun doword() {
        delay(10000L)
        println(", world")
    }
}


object HelloCoroutine2 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        val job = List(100_000) {
            launch {
                delay(1000L)
                println(Random().nextDouble())
            }
        }
        job.forEach{ it.join()}
    }

    suspend fun doword() {
        delay(10000L)
        println(", world")
    }
}

object HelloCoroutine3 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        launch {
            repeat(1000) {
                i ->
                println("I'm sleeping $i...")
                delay(500L)
            }
        }
        delay(120 * 1000L)
    }
}

// cancelling coroutine execution
object HelloCoroutine4 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        val job = launch {
            repeat(1000) {
                i ->
                println("I'm sleeping $i...")
                delay(500L)
            }
        }
        delay(3 * 1000L)
        println("main: I'm tired of waiting")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }
}

// cancellation is cooperative

object HelloCoroutine5 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        var startTime = System.currentTimeMillis()
        val job = launch {
            var nextPrintTime = startTime
            var i = 0
            while( i < 10 ) { // computation loop, just wastes CPU
                if(System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        delay(2 * 1000L)
        println("main: I'm tired of waiting")
        job.cancelAndJoin() // cancels the job and waits for job's completion
        println("main: Now I can quit.")
    }
}

// make computation code cancellable
object HelloCoroutine6 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        var startTime = System.currentTimeMillis()
        val job = launch {
            var nextPrintTime = startTime
            var i = 0
            while( i < 10 && isActive) { // computation loop, just wastes CPU
                if(System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        delay(2 * 1000L)
        println("main: I'm tired of waiting")
        job.cancelAndJoin() // cancels the job and waits for job's completion
        println("main: Now I can quit.")
    }
}

// coroutines dispatchers and threads
object HelloCoroutine7 {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking<Unit> {
        val jobs = ArrayList<Job>()
        jobs += launch {
            println("    'Unconfined': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch(coroutineContext) { // context of the parent, runBlocking coroutine
            println("     'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch { // will get dispatched to ForkJoinPool.commonPool (or equivalent)
            println("     'CommonPool': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs += launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("     'newSTC': I'm working in thread ${Thread.currentThread().name}")
        }
        jobs.forEach { it.join()}
    }
}