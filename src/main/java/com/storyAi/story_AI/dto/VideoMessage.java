package com.storyAi.story_AI.dto;

public class VideoMessage {
	  private Long id;
	   private String title;
	   private String Link;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public VideoMessage(Long id, String title, String link) {
		super();
		this.id = id;
		this.title = title;
		Link = link;
	}
	public VideoMessage() {
		super();
	}
	   
}
