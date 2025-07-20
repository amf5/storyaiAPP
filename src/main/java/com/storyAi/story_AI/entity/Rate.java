package com.storyAi.story_AI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Rate {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String description;
private Integer num;

@OneToOne()
//@JsonProperty(access = Access.WRITE_ONLY)
private User user;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public Integer getNum() {
	return num;
}

public void setNum(Integer num) {
	this.num = num;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Rate(String description, Integer num, User user) {
	super();
	this.description = description;
	this.num = num;
	this.user = user;
}

public Rate() {
	super();
}



}
