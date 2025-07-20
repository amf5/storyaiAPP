package com.storyAi.story_AI.kafka;



import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.dto.BookMessage;
import com.storyAi.story_AI.exception.ProducerException;

@Service
public class BookProducer {
private final KafkaTemplate<String , String> producer;


public BookProducer(KafkaTemplate<String , String> producer) {
	
	this.producer=producer;
}



public void sendBookMessage(Long id, String title) {
    try {
        BookMessage bookData = new BookMessage(id, title);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(bookData);
        producer.send("new-books", jsonMessage);
    } catch (Exception e) {
        throw new ProducerException();
    }
}




}
