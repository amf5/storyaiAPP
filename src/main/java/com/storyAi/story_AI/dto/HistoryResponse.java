package com.storyAi.story_AI.dto;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryResponse {
private LocalDateTime createdAt;
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
public HistoryResponse() {
	super();
}
/**
 * @param createdAt
 * @param nameBook
 * @param outhor
 * @param language
 * @param about
 * @param introduction
 * @param pages
 * @param coverImage
 */
public HistoryResponse(LocalDateTime createdAt, String nameBook, String outhor, String language, String about,
		String introduction, List<String> pages, String coverImage,LocalDateTime createdBook) {
	super();
	this.createdAt = createdAt;
	this.nameBook = nameBook;
	this.outhor = outhor;
	this.language = language;
	this.about = about;
	this.introduction = introduction;
	this.pages = pages;
	this.coverImage = coverImage;
	this.createdBook=createdBook;
}
public LocalDateTime getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
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
