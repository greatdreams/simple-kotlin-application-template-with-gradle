package com.greatdreams.learn.webflux.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@ComponentScan("com.greatdreams.learn.webflux.controller")
public class ApplicationConfigClass {
}
