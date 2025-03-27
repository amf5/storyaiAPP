package com.storyAi.story_AI.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class History {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;
private LocalDateTime createdAt;
@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinColumn(name = "user_id")
private User user;
@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinColumn(name = "book_id")
private Book book;
/**
 * 
 */
public History() {
	super();
}
/**
 * @param createdAt
 * @param user
 * @param book
 */
public History(LocalDateTime createdAt, User user, Book book) {
	super();
	this.createdAt = createdAt;
	this.user = user;
	this.book = book;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public LocalDateTime getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
}
