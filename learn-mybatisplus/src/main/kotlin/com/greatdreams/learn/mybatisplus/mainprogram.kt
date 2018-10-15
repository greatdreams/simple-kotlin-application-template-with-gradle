package com.greatdreams.learn.mybatisplus

import com.greatdreams.learn.mybatis.mapper.StudentMapper
import com.greatdreams.learn.mybatis.spring.MainProgram
import com.greatdreams.learn.mybatis.spring.springboot.SpringBootTestApplication
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.logging.LogFactory
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import org.slf4j.LoggerFactory

object MainProgram {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("application name is learn mybatis plus")
        log.info("The main program begins to run.")
        log.info("1 + 1= ${1 + 1}")
        log.info(System.getProperty("user.home"))

        // create a datasource
        val url = "jdbc:postgresql://127.0.0.1:5432/test"
        val username = "postgres"
        val password = "123456"
        val hikariCPConfig = HikariConfig()

        hikariCPConfig.jdbcUrl = url
        hikariCPConfig.username = username
        hikariCPConfig.password = password
        hikariCPConfig.minimumIdle = 10
        hikariCPConfig.maximumPoolSize = 100

        val dataSource = HikariDataSource(hikariCPConfig)

        //create a myatis configuration
        val mybatisConfig = Configuration()
        mybatisConfig.environment = Environment("develop", JdbcTransactionFactory(), dataSource)
        mybatisConfig.addMappers("com.greatdreams.learn.mybatis.mapper")
        mybatisConfig.typeAliasRegistry.registerAliases("com.greatdreams.learn.mybatis.model")
        LogFactory.useSlf4jLogging()

        // create SqlSessionFactory instance
        val sqlSessionFactory = SqlSessionFactoryBuilder().build(mybatisConfig)

        //create a SqlSession instance
        val session = sqlSessionFactory.openSession()
        try {
            val studentMapper = session.getMapper(StudentMapper::class.java)
            val student = studentMapper.selectStudentByID(2)
           log.debug("${student.id} ${student.name} ${student.telephone}")
        }finally {
            session.close()
        }
        log.info("The main program exits normally.")

        MainProgram.main(args)

        SpringBootTestApplication.main(args)
    }
}