package com.greatdreams.kotlin.template.spring.utils;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.lang.Nullable;

public class TypesafeConfigPropertySource extends EnumerablePropertySource<Config> {
    private static Logger logger = LoggerFactory.getLogger(TypesafeConfigPropertySource.class);
    public TypesafeConfigPropertySource(Config source) {
        super("typesafe-config", source);
    }

    @Override
    public String[] getPropertyNames() {

        return new String[0];
    }

    @Nullable
    @Override
    public Object getProperty(String name) {
        Object value = null;
        try {
            value = source.getValue(name).unwrapped();
        }catch (Exception e) {
            logger.warn("no such " + name + " property in the property source - " + this.name);
        }
        return value;
    }
}
