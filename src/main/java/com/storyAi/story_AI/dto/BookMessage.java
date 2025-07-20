package com.storyAi.story_AI.dto;

	public class BookMessage {
	    private Long id;
	    private String title;

	    public BookMessage() {
	    }

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

		public BookMessage(Long id, String title) {
			super();
			this.id = id;
			this.title = title;
		}

	}


