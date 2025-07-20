package com.storyAi.story_AI.security;

import com.storyAi.story_AI.entity.CustomUserDetails;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WebSocketFilter implements ChannelInterceptor {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository repository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String tokenHeader = accessor.getFirstNativeHeader("Authorization");

            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException(" Missing or invalid Authorization header");
            }

            String token = tokenHeader.substring(7);
            try {
                String username = tokenUtil.getEmailFromToken(token);
                System.out.println(" Extracted username: " + username);

                String storedToken = repository.findTokenByEmail(username);
                if (storedToken == null || !storedToken.equals(token)) {
                    throw new IllegalArgumentException(" Token not stored or mismatch");
                }

                CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(username);

                if (!tokenUtil.validateToken(token, userDetails)) {
                    throw new IllegalArgumentException(" Invalid token");
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
                System.out.println(" WebSocket authentication successful for: " + username);

            } catch (Exception e) {
                System.out.println("Exception in WebSocketFilter: " + e.getMessage());
                throw new IllegalArgumentException(" Token validation error: " + e.getMessage());
            }
        }

        return message;
    }
}
