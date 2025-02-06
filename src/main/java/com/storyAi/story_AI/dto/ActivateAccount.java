package com.storyAi.story_AI.dto;

public class ActivateAccount {
private String email;
private String code;
public ActivateAccount() {}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public ActivateAccount(String email, String code) {
	super();
	this.email = email;
	this.code = code;
}


}
