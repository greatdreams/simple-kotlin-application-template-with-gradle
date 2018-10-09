package com.greatdreams.learn.springboot

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableAutoConfiguration
object MainProgram {
    val logger = LoggerFactory.getLogger(MainProgram.javaClass)

    @RequestMapping("/")
    fun home() : String {
        return "Hello World"
    }

    @JvmStatic fun main(args : Array<String>) {
        logger.info("Welcome, springboot application!")
        val ctx = SpringApplication.run(MainProgram::class.java, *args)

        var i = 1
        ctx.beanDefinitionNames.forEach { beanName ->
            println("$i\t$beanName\t${ctx.getType(beanName).canonicalName}")
            i++
        }
    }
}