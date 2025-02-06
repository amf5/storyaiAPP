package com.storyAi.story_AI.dto;

public class ChangeMyPassword {

	private String password;
	private String email;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ChangeMyPassword () {}
	public ChangeMyPassword(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
