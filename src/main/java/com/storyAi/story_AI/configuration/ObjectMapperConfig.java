package com.storyAi.story_AI.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule()) 
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) 
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) 
                .enable(SerializationFeature.INDENT_OUTPUT); 
    }
}
