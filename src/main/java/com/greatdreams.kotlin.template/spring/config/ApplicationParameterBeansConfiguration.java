package com.greatdreams.kotlin.template.spring.config;

import com.greatdreams.kotlin.template.model.ApplicationInformation;
import com.greatdreams.kotlin.template.spring.utils.TypesafeConfigPropertySource;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import jdk.nashorn.internal.objects.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

@Configuration
public class ApplicationParameterBeansConfiguration {
    @Autowired
    private ConfigurableEnvironment env;

    @Bean("applicationConfiguration")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Lazy(false)
    public Config getApplicationConfig(){
        Config  config = ConfigFactory.load();
        return config;
    }

    @Bean
    @Autowired
    @Lazy(false)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public TypesafeConfigPropertySource getTypesafeConfigPropertySource(Config config) {
        TypesafeConfigPropertySource typesafeConfigPropertySource
                = new TypesafeConfigPropertySource(config);

        MutablePropertySources sources = env.getPropertySources();
        sources.addFirst(typesafeConfigPropertySource);

        return typesafeConfigPropertySource;
    }

    @Bean("applicationInformation")
    @Lazy
    public ApplicationInformation applicationInformation(
            @Value("${application.id:0x001}") String id,
            @Value("${application.name:anonymous}") String name,
            @Value("${application.author:greatdreams}") String author,
            @Value("${application.email:871155310@qq.com}") String email) {
        return new ApplicationInformation(id, name, author, email);
    }
}
