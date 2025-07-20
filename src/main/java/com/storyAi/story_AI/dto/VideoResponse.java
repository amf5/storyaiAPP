package com.storyAi.story_AI.dto;

import java.util.List;

public class VideoResponse {
private Long id;
private String linkVideo;
private String name;
private String about;
private List<String> paragraphs;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getLinkVideo() {
	return linkVideo;
}
public void setLinkVideo(String linkVideo) {
	this.linkVideo = linkVideo;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}
public List<String> getParagraphs() {
	return paragraphs;
}
public void setParagraphs(List<String> paragraphs) {
	this.paragraphs = paragraphs;
}
public VideoResponse(Long id, String linkVideo, String name, String about, List<String> paragraphs) {
	super();
	this.id = id;
	this.linkVideo = linkVideo;
	this.name = name;
	this.about = about;
	this.paragraphs = paragraphs;
}
public VideoResponse() {
	super();
}



}
