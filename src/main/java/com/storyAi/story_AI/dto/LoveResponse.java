package com.storyAi.story_AI.dto;

import java.time.LocalDateTime;
import java.util.List;

public class LoveResponse {
private LocalDateTime lovedAt;
private String nameBook;
private String outhor;
private String language;
private String about;
private String introduction;
private List<String>pages;
private String coverImage;
private LocalDateTime createdBook;
/**
 * 
 */
public LoveResponse() {
	super();
}
/**
 * @param lovedAt
 * @param nameBook
 * @param outhor
 * @param language
 * @param about
 * @param introduction
 * @param pages
 * @param coverImage
 */
public LoveResponse(LocalDateTime lovedAt, String nameBook, String outhor, String language, String about,
		String introduction, List<String> pages, String coverImage,LocalDateTime createdBook) {
	super();
	this.lovedAt = lovedAt;
	this.nameBook = nameBook;
	this.outhor = outhor;
	this.language = language;
	this.about = about;
	this.introduction = introduction;
	this.pages = pages;
	this.coverImage = coverImage;
	this.createdBook=createdBook;
}
public LocalDateTime getCreatedBook() {
	return createdBook;
}
public void setCreatedBook(LocalDateTime createdBook) {
	this.createdBook = createdBook;
}
public LocalDateTime getLovedAt() {
	return lovedAt;
}
public void setLovedAt(LocalDateTime lovedAt) {
	this.lovedAt = lovedAt;
}
public String getNameBook() {
	return nameBook;
}
public void setNameBook(String nameBook) {
	this.nameBook = nameBook;
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
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}
public String getIntroduction() {
	return introduction;
}
public void setIntroduction(String introduction) {
	this.introduction = introduction;
}
public List<String> getPages() {
	return pages;
}
public void setPages(List<String> pages) {
	this.pages = pages;
}
public String getCoverImage() {
	return coverImage;
}
public void setCoverImage(String coverImage) {
	this.coverImage = coverImage;
}
}
