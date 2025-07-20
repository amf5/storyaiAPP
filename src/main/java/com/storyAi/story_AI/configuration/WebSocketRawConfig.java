package com.storyAi.story_AI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.storyAi.story_AI.controller.AIWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketRawConfig implements WebSocketConfigurer {
	private final AIWebSocketHandler aiWebSocketHandler;
@Autowired
    public WebSocketRawConfig(AIWebSocketHandler aiWebSocketHandler) {
        this.aiWebSocketHandler = aiWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(aiWebSocketHandler, "/ai-websocket")
                .setAllowedOrigins("*"); 
    }

}
