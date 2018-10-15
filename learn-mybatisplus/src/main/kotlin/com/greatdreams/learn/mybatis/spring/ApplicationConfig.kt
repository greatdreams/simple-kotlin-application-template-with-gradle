package com.greatdreams.learn.mybatis.spring

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.logging.LogFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@Configuration
open class ApplicationConfig {

    @Bean
    open fun servletWebServerFactory() : ServletWebServerFactory {
        return TomcatServletWebServerFactory()
    }

    @Bean
    open fun dataSource(): DataSource {
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

       return HikariDataSource(hikariCPConfig)
    }

    @Bean(value = "sqlSessionFactory")
    open fun sqlSessionFactory(@Autowired dataSource: DataSource) : SqlSessionFactoryBean {
        LogFactory.useSlf4jLogging()

        val bean = SqlSessionFactoryBean().apply {
            setDataSource(dataSource)
            setTypeAliasesPackage("com.greatdreams.learn.mybatis.model")
            setMapperLocations(PathMatchingResourcePatternResolver().getResources("classpath:com/greatdreams/learn/mybatis/mapper/*.xml"))
        }
        return bean
    }

    @Bean
    open fun mapperScannerConfigurer(): MapperScannerConfigurer {
        val mapperScannerConfigurer = MapperScannerConfigurer()
        mapperScannerConfigurer.setBasePackage("com.greatdreams.learn.mybatis.mapper")
        return mapperScannerConfigurer
    }
}