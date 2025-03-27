package com.storyAi.story_AI.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long bookId;
private String outhor;
private String language;
private String nameBook;
private List<String>pages;
private String introduction;
private String coverImage;
private LocalDateTime createdBook;
public LocalDateTime getCreatedBook() {
	return createdBook;
}
public void setCreatedBook(LocalDateTime createdBook) {
	this.createdBook = createdBook;
}
@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
private User user;
private String about;
private boolean published;
public boolean isPublished() {
	return published;
}
public void setPublished(boolean published) {
	this.published = published;
}
public Long getBookId() {
	return bookId;
}
public void setBookId(Long bookId) {
	this.bookId = bookId;
}
public String getOuthor() {
	return outhor;
}
public void setOuthor(String outhor) {
	this.outhor = outhor;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}
public String getNameBook() {
	return nameBook;
}
public void setNameBook(String nameBook) {
	this.nameBook = nameBook;
}
public List<String> getPages() {
	return pages;
}
public void setPages(List<String> pages) {
	this.pages = pages;
}
public String getIntroduction() {
	return introduction;
}
public void setIntroduction(String introduction) {
	this.introduction = introduction;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}
/**
 * @param outhor
 * @param language
 * @param nameBook
 * @param pages
 * @param introduction
 * @param user
 * @param about
 */
public Book(String outhor, String language, String nameBook, List<String> pages, String introduction, User user,
		String about,String coverImage) {
	super();
	this.outhor = outhor;
	this.language = language;
	this.nameBook = nameBook;
	this.pages = pages;
	this.introduction = introduction;
	this.user = user;
	this.coverImage=coverImage;
	this.about = about;
}
public String getCoverImage() {
	return coverImage;
}
public void setCoverImage(String coverImage) {
	this.coverImage = coverImage;
}
/**
 * 
 */
public Book() {
	super();
}


}
