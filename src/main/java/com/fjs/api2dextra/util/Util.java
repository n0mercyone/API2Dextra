package com.fjs.api2dextra.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class Util {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getConfigValue(String configKey){
        return Environment.getProperties().getProperty(configKey);
    }

    public static String reponsePrettyJSON(Object value) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }
    
}
