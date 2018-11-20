package com.greatdreams.learn.springbatch

import com.greatdreams.learn.springbatch.config.ApplicationConfig
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.job.SimpleJob
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.batch.core.repository.support.SimpleJobRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableAutoConfiguration
@ComponentScan("com.greatdreams.learn.springbatch.")
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
            logger.debug("$i\t$beanName\t${ctx.getType(beanName).canonicalName}")
            i++
        }
    }
}
