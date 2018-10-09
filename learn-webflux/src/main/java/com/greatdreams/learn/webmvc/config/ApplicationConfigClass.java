package com.greatdreams.learn.webmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@ComponentScan("com.greatdreams.learn.webmvc.controller")
public class ApplicationConfigClass {
}
