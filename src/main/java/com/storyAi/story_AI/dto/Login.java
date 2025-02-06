package com.storyAi.story_AI.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Min;

public class Login {
	@NonNull
private String userName;
	@NonNull
	
private String password;
	
	
	public Login() {}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Login(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	
	
}
