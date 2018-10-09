package com.greatdreams.learn.rabbitmq

import com.rabbitmq.client.*
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object MainProgram {
    val log = LoggerFactory.getLogger(MainProgram.javaClass)
    val excutorService = Executors.newFixedThreadPool(20)

    @JvmStatic
    fun main(args: Array<String>) {
        log.info("begins to connect to rabbitmq server")

        val connFactory = ConnectionFactory()
        connFactory.username = "guest"
        connFactory.password = "guest"
        connFactory.host = "localhost"
        connFactory.port = 5672
        connFactory.virtualHost = "/"

        val con = connFactory.newConnection()

        // open a channel
        val channel = con.createChannel()

        /*
        val exchangeName = "test_exchange"
        val routeKey = "test_queue"

        // declare a exchange type of which  is direct
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true)

        // declare a queue
        val queue = channel.queueDeclare("test_queue",
                true, false, false , null).queue

        // queue bingding a exchange
        channel.queueBind(queue, "test_exchange", routeKey)

        val message = "Hello rabbitmq".toByteArray()
        for(i in 1..1000000000) {
            // send message
            channel.basicPublish(exchangeName, routeKey, MessageProperties.PERSISTENT_BASIC, message)
            Thread.sleep(1)
        }
        */

        val producers = listOf<Producer>(Producer(con, "producer-1"),
                Producer(con, "producer-2"),
                Producer(con, "producer-3"),
                Producer(con, "producer-4"),
                Producer(con, "producer-5"),
                Producer(con, "producer-6"),
                Producer(con, "producer-7"),
                Producer(con, "producer-8"),
                Producer(con, "producer-9"),
                Producer(con, "producer-10"),
                Producer(con, "producer-11")
        )

        producers.forEach { producer ->
            excutorService.submit(producer)
        }

        val consumers = listOf<Consumer>(Consumer(con, id = "consumer-1"),
                Consumer(con, id = "consumer-2"),
                Consumer(con, id = "consumer-3"),
                Consumer(con, id = "consumer-4"),
                Consumer(con, id = "consumer-5"))
        consumers.forEach { consumer ->
            excutorService.execute(consumer)
        }
        excutorService.awaitTermination(1, TimeUnit.DAYS)
    }
}

class Producer(val connection: Connection, val id: String) : Runnable {

    override fun run() {
        // open a channel
        val channel = connection.createChannel()

        val exchangeName = id
        val routeKey = "test_route"

        // declare a exchange type of which  is direct
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true)

        // declare a queue
        val queue = channel.queueDeclare("test_queue",
                true, false, false, null).queue

        // queue bingding a exchange
        channel.queueBind(queue, exchangeName, routeKey)


        for (i in 1..100000000) {
            val message = "${id} is sending Hello rabbitmq ${i}}".toByteArray()
            // send message
            channel.basicPublish(exchangeName, routeKey, MessageProperties.PERSISTENT_BASIC, message)
            // Thread.sleep(1)
        }
    }
}

class Consumer(val connection: Connection, val id: String) : Runnable {
    val log = LoggerFactory.getLogger(Consumer::class.java)
    override fun run() {
        // open a channel
        val channel = connection.createChannel()
        val consumer = CustomerConsumer(channel, id)


        val queue = channel.queueDeclare("test_queue", true, false, false, null)
        log.info("${id} begins to consumer message(${queue.messageCount})")
        channel.basicConsume("test_queue", true, consumer)
    }
}

class CustomerConsumer(channel: Channel, val id: String) : DefaultConsumer(channel) {
    val log = LoggerFactory.getLogger(CustomerConsumer::class.java)
    override fun handleDelivery(
            consumerTag: String?,
            envelope: Envelope?,
            properties: AMQP.BasicProperties?,
            body: ByteArray?) {

        val bodyString = String(body!!, Charsets.UTF_8)
        log.info("${id}: ${bodyString}")
        Thread.sleep(10)
    }
}

