package com.storyAi.story_AI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ObjectMapperService {

    @Autowired
    private ObjectMapper objectMapper;

    public String convertObjectToJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
}
