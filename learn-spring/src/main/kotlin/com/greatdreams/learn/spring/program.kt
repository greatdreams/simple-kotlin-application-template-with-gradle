package com.greatdreams.learn.spring

import com.greatdreams.learn.spring.task.MessagePrinterTask
import com.greatdreams.learn.spring.task.TaskExecutorExample
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import java.util.*

val logger = LoggerFactory.getLogger("SpringMainProgram")
fun main() {
    val ctx = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
    val taskExecutorExample = ctx.getBean(TaskExecutorExample::class.java)
    val scheduler = ctx.getBean(TaskScheduler::class.java)

    taskExecutorExample.printMessage()

    scheduler.schedule(MessagePrinterTask("Schduler Message ${Random().nextDouble()}"),
            CronTrigger("10 * * * * MON-FRI"))

    logger.info("learn spring")
}