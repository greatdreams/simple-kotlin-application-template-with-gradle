package com.greatdreams.learn.mybatis.spring.springboot

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableAutoConfiguration(exclude=[DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
@RestController
@ComponentScan(basePackages = ["com.greatdreams.learn.mybatis.spring.springboot.controller", "com.greatdreams.learn.mybatis.spring.springboot.init"])
object SpringBootTestApplication : SpringBootServletInitializer() {
    val log = LoggerFactory.getLogger(SpringBootTestApplication::class.java)

  /*  @RequestMapping("/index")
    fun home() : String {
        return "Hello World"
    }*/

    @JvmStatic fun main(args: Array<String>) {
        SpringApplication.run(SpringBootTestApplication::class.java)
    }
}