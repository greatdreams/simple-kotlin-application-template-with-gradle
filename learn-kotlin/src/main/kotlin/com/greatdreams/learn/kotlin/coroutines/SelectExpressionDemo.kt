package com.greatdreams.learn.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.fizz() = produce<String> {
    while(true) {
        delay(300)
        send("Fizz")
    }
}
fun CoroutineScope.buzz() = produce<String> {
    while(true) {
        delay(500)
        send("Buzz")
    }
}

suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
    select<Unit> {// <Unit> means that this select expression does not produce any result
        fizz.onReceive {
            value -> // this is the first select clause
            println("fizz -> $value")
        }
        buzz.onReceive {
            value -> // this is the second select clause
            println("buzz -> $value")
        }
    }
}

fun main() = runBlocking {
    val fizz = fizz()
    val buzz = buzz()
    repeat(100) {
        selectFizzBuzz(fizz, buzz)
    }
    coroutineContext.cancelChildren() // cancel fizz & buzz coroutines
}