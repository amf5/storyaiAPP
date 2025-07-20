package com.storyAi.story_AI.mapper;

import com.storyAi.story_AI.dto.VideoResponse;
import com.storyAi.story_AI.entity.Video;

public class VideoResponseMapper {
public static VideoResponse toVideoResponse(Video video) {
	
	VideoResponse videoResponse=new VideoResponse();
	videoResponse.setId(video.getId());
	videoResponse.setAbout(video.getAbout());
	videoResponse.setLinkVideo(video.getLinkVideo());
	videoResponse.setName(video.getName());
	videoResponse.setParagraphs(video.getParagraphs());
	return videoResponse;
}
}
