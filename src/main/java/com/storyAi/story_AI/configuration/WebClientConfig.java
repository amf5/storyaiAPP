package com.storyAi.story_AI.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        
        HttpClient httpClient = HttpClient.create(ConnectionProvider.create("fixed", 100)) 
                .responseTimeout(Duration.ofSeconds(60)) 
                .wiretap(true); 

        return WebClient.builder()
                .baseUrl("https://4ed2-102-185-202-78.ngrok-free.app")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(16 * 1024 * 1024)) 
                .filter((request, next) -> next.exchange(request) 
                        .doOnNext(response -> {
                           
                            if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
                                throw new RuntimeException("Request failed with status: " + response.statusCode());
                            }
                        }))
                .build();
    }
}

