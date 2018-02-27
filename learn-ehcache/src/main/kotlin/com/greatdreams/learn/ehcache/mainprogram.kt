package com.greatdreams.learn.ehcache

import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.slf4j.LoggerFactory


object MainClass {
    val log = LoggerFactory.getLogger(javaClass)

    @JvmStatic fun main(args: Array<String>) {
        log.info("application name: learn ehcache")
        log.info("begins to run application")

        val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long::class.java,
                        String::class.java,
                        ResourcePoolsBuilder.heap(100L))
                        .build())
                .build(true)

        val preConfigured = cacheManager.getCache("preCOnfigured", Long::class.java, String::class.java)

        val myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long::class.java, String::class.java,
                        ResourcePoolsBuilder.heap(100)).build())

        myCache.put(1L, "ehcache")

        log.info(myCache.get(1L))

        cacheManager.close()

        log.info("application exits normally")
    }
}