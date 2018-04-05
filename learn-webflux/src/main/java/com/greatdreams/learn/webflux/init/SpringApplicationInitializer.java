package com.greatdreams.learn.webflux.init;

import com.greatdreams.learn.webflux.config.ApplicationConfigClass;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

public class SpringApplicationInitializer extends AbstractReactiveWebInitializer {
    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{ApplicationConfigClass.class};
    }
}
