package com.greatdreams.learn.webmvc.init

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class SpringApplicationInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>> {
        return arrayOf(ApplicationConfigClass::class.java)
    }

    override fun getServletConfigClasses(): Array<Class<*>> {
        return arrayOf()
    }

    override fun getServletMappings(): Array<String> {
        return arrayOf("/")
    }
}

@Configuration
@EnableWebMvc
@ComponentScan("com.greatdreams.learn.webmvc.controller")
class ApplicationConfigClass
