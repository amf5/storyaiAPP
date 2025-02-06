package com.storyAi.story_AI.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.storyAi.story_AI.entity.CustomUserDetails;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.service.CustomUserDetailsService;
import com.storyAi.story_AI.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;


import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private UserRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	
        final String token = getTokenFromRequest(request);
        String username = null;

        if (token != null ) {
        
        	username = tokenUtil.getEmailFromToken(token);
        String storeToken=repository.findTokenByEmail(username);
        if(storeToken!=null) {
        	if( tokenUtil.validateToken(token, userService.loadUserByUsername(username))&&
        			storeToken.equals(token)
        			)
        	{
        		
           
            CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(username);


           
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails.getEmail(), null, userDetails.getAuthorities()));
        }}
        	
        }

       
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
