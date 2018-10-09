package com.greatdreams.learn.redis

import redis.clients.jedis.Jedis

object MainProgram {
    @JvmStatic fun main(args: Array<String>) {
        val host = "127.0.0.1"
        val port = 6379
        val jedis = Jedis(host, port)
        jedis.set("foo", "bar")
        val value = jedis.get("foo")
        println(value)
        jedis.close()
    }
}