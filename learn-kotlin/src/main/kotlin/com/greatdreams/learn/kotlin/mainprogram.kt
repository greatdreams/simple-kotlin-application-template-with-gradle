package com.greatdreams.learn.kotlin

import com.greatdreams.learn.kotlin.classes.LearnClasses
import com.greatdreams.learn.kotlin.classes.LearnClasses1
import com.greatdreams.learn.kotlin.delegate.DelegateTestProgram
import com.greatdreams.learn.kotlin.lambda.LambdaTestProgram
import com.greatdreams.learn.kotlin.with.WithTest
import kotlinx.coroutines.*

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

        DelegateTestProgram.main(args)
        LambdaTestProgram.main()
    }
}