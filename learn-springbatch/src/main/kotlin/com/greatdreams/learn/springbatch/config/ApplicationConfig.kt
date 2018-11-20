package com.greatdreams.learn.springbatch.config

import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.util.Assert
import java.util.concurrent.atomic.AtomicInteger

@Configuration
@EnableBatchProcessing
open class ApplicationConfig(@Autowired val jobs: JobBuilderFactory,
                             @Autowired val steps: StepBuilderFactory) {

    @Bean(name = ["jobLancher"])
    open fun jobLauncher(@Autowired jobRepository: JobRepository): JobLauncher {
        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setJobRepository(jobRepository)
        jobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor())
        jobLauncher.afterPropertiesSet()
        return jobLauncher
    }
    @Bean
    open fun job(@Qualifier("step1") step1: Step, @Qualifier("step2") step2: Step): Job {
        // return jobs.get("myJob").start(step1).next(step2).build()
        val job = jobs.get("myJob")
                .start(step1)
                .build()
        return job
    }

    @Bean
    open fun reader(): ItemReader<String> {
        return SimpleItemReader()
    }
    @Bean
    open fun writer(): ItemWriter<String> {
        return SimpleItemWriter()
    }

    @Bean
    open fun processor(): ItemProcessor<String, String>{
        return SimpleItemProcessor()
    }

    @Bean
    open fun step1(reader: ItemReader<String>,
                   processor: ItemProcessor<String, String>,
                   writer: ItemWriter<String>): Step{

        return steps.get("step1")
                .chunk<String, String>(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build()

    }

    @Bean
    open fun tasklet(): Tasklet {
        return FileDeletingTasklet(FileSystemResource("/djafjadk;ja"))
    }
    @Bean
    open fun step2(tasklet: Tasklet): Step {
        return steps.get("step2")
                .tasklet(tasklet)
                .build()
    }
}

class SimpleItemReader: ItemReader<String> {
    companion object {
        val count: AtomicInteger = AtomicInteger(10)
    }
    override fun read(): String? {
        if(count.decrementAndGet() > 0)
            return "hello string"
        else
            return null
    }
}

class SimpleItemWriter: ItemWriter<String> {
    override fun write(items: MutableList<out String>) {
        println("${items.toString()}")
    }

}

class SimpleItemProcessor: ItemProcessor<String, String> {
    companion object {
        val logger = LoggerFactory.getLogger(SimpleItemProcessor::class.java)
    }
    override fun process(item: String): String? {
        logger.info("current proccessing item is $item")
        return item.toUpperCase()
    }
}

class FileDeletingTasklet(val directory: Resource) :Tasklet, InitializingBean {
    override fun execute(contribution: StepContribution,
                         chunkContext: ChunkContext): RepeatStatus?{
        val dir = directory.file
        Assert.state(dir.isDirectory)
        val files = dir.listFiles()
        files.forEach {
            val deleted = it.delete()
            if(!deleted) {
                throw UnexpectedJobExecutionException("Could not delete file ${it.path}")
            }
        }
        return RepeatStatus.FINISHED
    }
    override fun afterPropertiesSet() {
        Assert.notNull(directory, "directory must be set")
    }

}

class SimpleJobExecutionListener: JobExecutionListener {
    companion object {
        val logger = LoggerFactory.getLogger(SimpleJobExecutionListener::class.java)
    }
    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("code before a job")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("code after a job")
        if(jobExecution.status == BatchStatus.COMPLETED) {
            logger.info("status of job is success")

        }else if(jobExecution.status == BatchStatus.FAILED){
            logger.info("status of job is failed")
        }
    }
}