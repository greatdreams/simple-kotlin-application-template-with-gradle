package com.greatdreams.learn.kotlin.ktor

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun main() {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}