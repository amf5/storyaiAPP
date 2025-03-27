package com.storyAi.story_AI.dto;

import java.time.LocalDateTime;
import java.util.List;



public class BookDto {
private Long bookId;
private String nameBook;
private String outhor;
private String language;
private String about;
private String introduction;
private List<String>pages;
private String coverImage;
private LocalDateTime createdBook;
public LocalDateTime getCreatedBook() {
	return createdBook;
}
public void setCreatedBook(LocalDateTime createdBook) {
	this.createdBook = createdBook;
}
public BookDto(Long bookId,String outhor, String language, String nameBook, List<String> pages, String introduction, 
		String about,String coverImage,LocalDateTime createdBook) {
	super();
	this.outhor = outhor;
	this.language = language;
	this.nameBook = nameBook;
	this.pages = pages;
	this.introduction = introduction;
	this.coverImage=coverImage;
	this.about = about;
	this.createdBook=createdBook;
	this.bookId=bookId;
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
public Long getBookId() {
	return bookId;
}
public void setBookId(Long bookId) {
	this.bookId = bookId;
}
/**
 * 
 */
public BookDto() {
	super();
}

}
