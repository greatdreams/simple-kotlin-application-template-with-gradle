package com.greatdreams.learn.vertx.websocket

import io.vertx.core.Vertx
import org.slf4j.LoggerFactory

object WebsocketServerTest {
    val logger = LoggerFactory.getLogger(WebsocketServerTest.javaClass)
    @JvmStatic fun main(args: Array<String>) {
        logger.info("Start Websocket Server ...")
        val vertx = Vertx.vertx()
        val server = vertx.createHttpServer()
        server.websocketHandler {
                ws ->
            ws.handler {
                ws::writeTextMessage
            }
        }.requestHandler {
            req ->
            if (req.uri().equals("/")) req.response().write("Hello, websocket")
        }
        server.listen(8090)
    }
}