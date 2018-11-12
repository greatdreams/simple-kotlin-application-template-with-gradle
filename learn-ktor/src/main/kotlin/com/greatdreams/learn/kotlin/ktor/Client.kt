package com.greatdreams.learn.kotlin.ktor
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking

fun main() {
    val client = HttpClient(Apache)

    runBlocking {
        val htmlContent = client.get<String>("https://en.wikipedia.org/wiki/Main_Page")
        println(htmlContent)
    }
}