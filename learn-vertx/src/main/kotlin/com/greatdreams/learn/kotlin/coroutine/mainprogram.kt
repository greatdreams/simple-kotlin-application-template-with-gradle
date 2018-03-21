package com.greatdreams.learn.kotlin.coroutine

import io.vertx.core.Vertx

object MainProgram {
    @JvmStatic fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        val server = vertx.createHttpServer()
        // this handler gets called for each request that arrives
        // on the server
        server.requestHandler({ request ->
            var response = request.response()
            response.putHeader("content-type", "text/plain")
            response.end("Hello, vertx web server")
        })

        server.listen(8080)
    }
}