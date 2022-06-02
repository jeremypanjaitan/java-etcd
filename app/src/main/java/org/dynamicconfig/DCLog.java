package org.dynamicconfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;

public class DCLog {

    public static void logInfoConfig(Class<?> clazz, Config message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Logger.getLogger(clazz.getName()).info(mapper.writeValueAsString(message));

    }

    public static void logInfoMessage(Class<?> clazz, String message) throws JsonProcessingException {
        Logger.getLogger(clazz.getName()).info(message);
    }
}
