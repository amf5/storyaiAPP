package com.storyAi.story_AI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StoryAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryAiApplication.class, args);
	}

}
