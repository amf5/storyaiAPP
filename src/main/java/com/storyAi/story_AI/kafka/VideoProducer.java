package com.storyAi.story_AI.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.dto.VideoMessage;
import com.storyAi.story_AI.exception.ProducerException;

@Service
public class VideoProducer {
private final KafkaTemplate<String , String> producer;
private final ObjectMapper objectMapper;

public VideoProducer(KafkaTemplate<String , String> producer,ObjectMapper objectMapper) {
this.objectMapper=objectMapper;
	this.producer=producer;
}


public void sendKafkaProducerVideo(Long id,String title,String link) throws JsonProcessingException {
	
	try{VideoMessage videoMessage=new VideoMessage(id, title, link);
	String Message=objectMapper.writeValueAsString(videoMessage);
	producer.send("new-videos",Message);
	
	}catch (Exception e) {
        throw new ProducerException();
    }
}

}
