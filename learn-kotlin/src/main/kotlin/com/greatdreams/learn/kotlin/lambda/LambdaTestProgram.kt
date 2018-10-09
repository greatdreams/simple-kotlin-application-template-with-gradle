package com.greatdreams.learn.kotlin.lambda

val sum = { x: Int, y: Int -> x + y }

object LambdaTestProgram {
    fun main() {
        println(sum(10, 100))
        println(sum::class.java)
        sum::class.java.genericInterfaces.forEach { type ->
            println(type::class.java)
        }
    }
}