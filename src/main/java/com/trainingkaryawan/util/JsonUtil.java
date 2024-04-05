package com.trainingkaryawan.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtil {

    private JsonUtil() {
    }

    static ObjectMapper objectMapper;

    public static <T> String getString(T request) {
        String xmlString = "";
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("error failed to get json string", e);
        }
        return xmlString;
    }

    public static Object mapObject(String data, Object object) {
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
            }
            return objectMapper.readValue(data, object.getClass());
        } catch (IOException e) {
            log.error("error map object failed {}", e.getMessage());
            return null;
        }
    }
}
