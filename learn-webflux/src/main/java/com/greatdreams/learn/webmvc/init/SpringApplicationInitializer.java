package com.greatdreams.learn.webmvc.init;

import com.greatdreams.learn.webmvc.config.ApplicationConfigClass;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

public class SpringApplicationInitializer extends AbstractReactiveWebInitializer {
    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{ApplicationConfigClass.class};
    }
}
