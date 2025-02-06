package com.storyAi.story_AI.entity;

import java.time.LocalDateTime;
import java.util.Set;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getProvider() {
	return provider;
}

public void setProvider(String provider) {
	this.provider = provider;
}

@Column(nullable = false)	
@NonNull
private String firstName;
@Column(nullable = false)	
@NonNull
private String LastName;
@Column(unique = true,nullable = false)	
@NonNull
@Email
private String email;

private String password;
private String verificationCode;
private boolean status;
@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinTable(name="User_Role", joinColumns = {
	@JoinColumn(name="User_Id")
}, inverseJoinColumns = @JoinColumn(name="Role_Id"))
private Set<Role> roles;
@Column(name = "verification_code_sent_at")
private LocalDateTime verificationCodeSentAt;
@Column(unique = true,length = 512)	

private String token;

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

private String provider;
public LocalDateTime getVerificationCodeSentAt() {
	return verificationCodeSentAt;
}

public void setVerificationCodeSentAt(LocalDateTime verificationCodeSentAt) {
	this.verificationCodeSentAt = verificationCodeSentAt;
}

public User() {}

public User( String firstName, String lastName, @Email String email, String password, boolean status,Set<Role>roles) {
	super();

	this.firstName = firstName;
	LastName = lastName;
	this.email = email;
	this.password = password;
	this.status = status;
	this.roles=roles;
}

public Set<Role> getRoles() {
	return roles;
}

public void setRoles(Set<Role> roles) {
	this.roles = roles;
}


public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return LastName;
}

public void setLastName(String lastName) {
	LastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getVerificationCode() {
	return verificationCode;
}

public void setVerificationCode(String verificationCode) {
	this.verificationCode = verificationCode;
}

public boolean isStatus() {
	return status;
}

public void setStatus(boolean status) {
	this.status = status;
}









}
