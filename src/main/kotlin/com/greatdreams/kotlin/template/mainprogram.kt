package com.greatdreams.kotlin.template

import org.slf4j.LoggerFactory

/**
 * @author Greatdreams
 * Created by greatdreams on 1/9/17.
 * Use [kotlin.reflect.KClass.declaredMemberExtensionProperties] to enumerate
 */
object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("The main program begins to run.")
        /*
        val ctx = AnnotationConfigApplicationContext("com.greatdreams.kotlin.template.spring.config")
        val beanNames = ctx.beanDefinitionNames
        beanNames.forEach{ name ->
            log.info(name + ": " + ctx.getBean(name).javaClass)
        }
        val applictionConfig = ctx.getBean("applicationConfiguration")
        val applicationInformation = ctx.getBean("applicationInformation")
        log.info(applicationInformation.toString())
        log.info(Charset.forName("gb2312").displayName())
        */
        log.info("The main program exits normally.")
    }
}