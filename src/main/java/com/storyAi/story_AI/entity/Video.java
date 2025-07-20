package com.storyAi.story_AI.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String LinkVideo;
	private String creator;
	private String language;
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	private String name;
	private String about;
	@ElementCollection
	private List<String> paragraphs;
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JsonProperty(access = Access.READ_WRITE)
	@JoinColumn(name = "user_id")
	private User user;
	 
	private boolean published=false;
	
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLinkVideo() {
		return LinkVideo;
	}
	public void setLinkVideo(String linkVideo) {
		LinkVideo = linkVideo;
	}
	public List<String> getParagraphs() {
		return paragraphs;
	}
	public void setParagraphs(List<String> paragraphs) {
		this.paragraphs = paragraphs;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video(String linkVideo, List<String> paragraphs, User user) {
		super();
		LinkVideo = linkVideo;
		this.paragraphs = paragraphs;
		this.user = user;
	}
	public Video() {
		super();
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
      
}
