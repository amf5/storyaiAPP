package com.storyAi.story_AI.dto;

public class AuthResponse {
private Long id;
private String token;
/**
 * 
 */
public AuthResponse() {
	super();
}
/**
 * @param id
 * @param token
 */
public AuthResponse(Long id, String token) {
	super();
	this.id = id;
	this.token = token;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
}
