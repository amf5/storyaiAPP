package com.storyAi.story_AI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Signup {

private String password;
private String firstName;
private String lastName;
@Email
private String email;


public Signup() {}




public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}


public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public Signup( String password, String firstName, String lastName,
		@Email String email) {
	super();

	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
}

}
