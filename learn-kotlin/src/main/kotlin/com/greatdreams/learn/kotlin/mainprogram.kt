package com.greatdreams.learn.kotlin

import com.greatdreams.learn.kotlin.classes.LearnClasses
import com.greatdreams.learn.kotlin.classes.LearnClasses1
import com.greatdreams.learn.kotlin.delegate.DelegateTestProgram
import com.greatdreams.learn.kotlin.lambda.LambdaTestProgram
import com.greatdreams.learn.kotlin.with.WithTest
import kotlinx.coroutines.*

fun String.removeLastChar() : String = this.substring(0, this.length -1)

class HTML {
    fun body() {  }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // create the receiver object
    html.init()        // pass the receiver object to the lambda
    return html
}

object MainProgram {
    @JvmStatic fun main(args: Array<String>) {
       /*
        WithTest.main(args)

        launch {
            delay(2000L)
            println("coroutines!")
        }
        println("Hello, ")
        runBlocking {
            delay(3000L)
        }
        LearnClasses.main(args)
        LearnClasses1.main(args)
        */
        println("Welcome, learn kotlin")

        // DelegateTestProgram.main(args)
        /// LambdaTestProgram.main()

        val message = "Hello world"
        println(message.removeLastChar())


        html {       // lambda with receiver begins here
            body()   // calling a method on the receiver object
        }

    }
}

class Utils {
    fun String.removeLastChar(): String = this.substring(0, this.length - 1)
}