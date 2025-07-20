package com.storyAi.story_AI.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.storyAi.story_AI.dto.ModelAiRequest;

import reactor.core.publisher.Mono;

@Service
public class ModelAiService {

    @Autowired
    private WebClient webClient;

    public Mono<Map<String, Object>> sendFirstRequestToKnowPlaceOfVideo(ModelAiRequest request) {
        return webClient.post()
                .uri("/submit-prompts/") 
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
 
       
    }

    public Mono<byte[]> downloadVideo(String jobId) {
        return webClient.get()
                .uri("/download/{job_id}", jobId) 
                .retrieve()
                .bodyToMono(byte[].class); 
    }
}
