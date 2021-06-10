package com.fjs.api2dextra.util;


import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    public static String getConfigValue(String configKey){
        return Environment.getProperties().getProperty(configKey);
    }
}
