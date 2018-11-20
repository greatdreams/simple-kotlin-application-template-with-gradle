package com.greatdreams.learn.spring.task

import org.springframework.core.task.TaskExecutor

class TaskExecutorExample(val taskExecutor: TaskExecutor) {
    open fun printMessage() {
        (1..25).forEach {
            taskExecutor.execute(MessagePrinterTask("Message $it"))
        }
    }
}