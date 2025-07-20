package com.storyAi.story_AI.kafka;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.domain.NotificationStatus;

import com.storyAi.story_AI.dto.VideoMessage;

import com.storyAi.story_AI.entity.Notification;
import com.storyAi.story_AI.entity.Video;

import com.storyAi.story_AI.repository.NotificationRepository;
import com.storyAi.story_AI.repository.VideoRepository;

@Component
public class VideoConsumer {
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired 
    private VideoRepository videoRepository;
    @KafkaListener(topics = "new-videos", groupId = "video-group", containerFactory = "videoKafkaListenerContainerFactory")
    public void consume(VideoMessage videoMessage) throws Exception {
    	Video video=videoRepository.findById(videoMessage.getId()).orElseThrow(()-> new Exception("video not fount"));
  	messagingTemplate.convertAndSend("/topic/new-videos", videoMessage);
  	Notification notification=new Notification();
  	notification.setCreatedAt(LocalDateTime.now());
  	notification.setVideo(video);
  	notification.setStatus(NotificationStatus.VIDEO);
  	notificationRepository.save(notification);
    	 System.out.print(" Message video received and sent via WebSocket");
    	  
    }
}
