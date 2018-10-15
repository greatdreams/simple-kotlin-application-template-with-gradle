package com.greatdreams.learn.mybatis.spring

import com.greatdreams.learn.mybatis.mapper.StudentMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object MainProgram {
    val log = LoggerFactory.getLogger(MainProgram::class.java)

    @JvmStatic fun main(args: Array<String>) {
        val ctx = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
        ctx.beanDefinitionNames.forEach { beanName ->
            log.debug("$beanName ${ctx.getBean(beanName).javaClass}")
        }

        val studentMapper = ctx.getBean(StudentMapper::class.java)
        val student = studentMapper.selectStudentByID(1)
        log.debug("${student.id} ${student.name} ${student.telephone}")
    }
}