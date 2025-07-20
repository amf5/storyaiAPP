package com.storyAi.story_AI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.storyAi.story_AI.security.WebSocketFilter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
	   @Autowired
	    private WebSocketFilter webSocketFilter;
   @Override
   public void configureMessageBroker(MessageBrokerRegistry config) {
       config.enableSimpleBroker("/topic"); 
       config.setApplicationDestinationPrefixes("/app"); 
   }

   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
       registry.addEndpoint("/ws-books"). setAllowedOrigins("http://localhost:5500") .withSockJS();
       registry.addEndpoint("/ws-videos").setAllowedOrigins("http://localhost:5500").withSockJS();

   }
   @Override
   public void configureClientInboundChannel(ChannelRegistration registration) {
       registration.interceptors(webSocketFilter);
   }
   } 