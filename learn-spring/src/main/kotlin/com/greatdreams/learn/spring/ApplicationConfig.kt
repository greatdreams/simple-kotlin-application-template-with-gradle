package com.greatdreams.learn.spring

import com.greatdreams.learn.spring.task.TaskExecutorExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
open class ApplicationConfig {
    @Bean
    open fun taskExecutor(): TaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = 5
        taskExecutor.maxPoolSize = 10
        taskExecutor.setQueueCapacity(25)
        return taskExecutor
    }
    @Bean
    open fun taskExecutorExample(@Autowired taskExecutor: TaskExecutor): TaskExecutorExample {
        return TaskExecutorExample(taskExecutor())
    }

    @Bean
    open fun scheduler(): TaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = 30
        return scheduler
    }
}