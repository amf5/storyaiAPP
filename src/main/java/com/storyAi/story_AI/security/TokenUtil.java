package com.storyAi.story_AI.security;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Component;

import com.storyAi.story_AI.entity.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenUtil {

    private static final String SECRET_KEY = "hIq7PODBVRTbqWyCAcZ0ytRdUWqai+fk7iPuSL8YnW0=";

    
    private static final int TOKEN_VALIDITY = 30 * 24 * 60 * 60 * 1000; 


    
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

   
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(SECRET_KEY) 
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Error while parsing token: " + e.getMessage(), e);
        }
    }

    
    public boolean validateToken(String token, CustomUserDetails userDetails) {
        String email = getEmailFromToken(token);
        return (email.equals(userDetails.getEmail()) 
                && !isTokenExpired(token)
                && validateTokenSignature(token));
    }

    
    public boolean validateTokenSignature(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

   
    public boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

   
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

   
    
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
public Long getIdFromBearerJwt(String bearerToken ) {
	
	if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        bearerToken= bearerToken.substring(7);
        return getClaimFromToken(bearerToken, claims -> claims.get("userId", Long.class));    
    }
    return null;
	
	
}


public Long getIdFromJwtWithoutBearer(String token) {

	if (token != null ) {
       
        return getClaimFromToken(token, claims -> claims.get("userId", Long.class));    
    }
    return null;
	
}
    
    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userDetails.getFirstname() + " " + userDetails.getLastname());
        claims.put("Status", userDetails.isEnabled());
        claims.put("userId", userDetails.getId());
        List<String> roles = userDetails.getAuthorities().stream()
                                         .map(GrantedAuthority::getAuthority)
                                         .collect(Collectors.toList());
        claims.put("Roles", roles);

        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(userDetails.getEmail())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                   .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                   .compact();
    }
}
