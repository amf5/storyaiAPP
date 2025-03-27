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
public class Love {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private LocalDateTime lovedAt;
@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinColumn(name = "user_id")
private User user;
@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinColumn(name = "book_id")
private Book book;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public LocalDateTime getLovedAt() {
	return lovedAt;
}
public void setLovedAt(LocalDateTime lovedAt) {
	this.lovedAt = lovedAt;
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
/**
 * @param lovedAt
 * @param user
 * @param book
 */
public Love(LocalDateTime lovedAt, User user, Book book) {
	super();
	this.lovedAt = lovedAt;
	this.user = user;
	this.book = book;
}
/**
 * 
 */
public Love() {
	super();
}

}
