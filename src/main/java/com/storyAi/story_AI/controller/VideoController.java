package com.storyAi.story_AI.controller;




import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storyAi.story_AI.dto.VideoResponse;
import com.storyAi.story_AI.entity.Video;
import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.VideoService;

@RestController
@RequestMapping("/user/video")
public class VideoController {
	
private final VideoService videoService;
private final TokenUtil tokenUtil;

public VideoController(VideoService videoService,TokenUtil tokenUtil ) {
	this.tokenUtil=tokenUtil;
	this.videoService=videoService;
	
}
@GetMapping("/{userId}")
public ResponseEntity<List<VideoResponse>>getMyVideos(@PathVariable Long userId,@RequestHeader ("Authorization")String jwt) throws Exception{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userIdCompare.equals(userId)) {
		
		throw new Exception("user not found with this id: "+userId);
	}
	return ResponseEntity.ok(videoService.MyVideos(userIdCompare));
	
	
}

@PatchMapping("/publish-uppublish")
public ResponseEntity<String>doPublishORUnpublish(@RequestHeader ("Authorization")String jwt
		,@RequestParam Long userId,@RequestParam Long videoId,@RequestParam boolean published) throws Exception{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userIdCompare.equals(userId)) {
		
		throw new Exception("user not found with this id: "+userId);
	}
	return new ResponseEntity(videoService.doAndDonotPublish(userIdCompare, videoId, published),HttpStatus.ACCEPTED);
}

@GetMapping("/published-videos")
public ResponseEntity<List<Video>> getPublishedVideos(){
	
	return ResponseEntity.ok(videoService.getPublishedVideos());
}





}
