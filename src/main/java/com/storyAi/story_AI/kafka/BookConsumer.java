package com.storyAi.story_AI.kafka;



import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.domain.NotificationStatus;
import com.storyAi.story_AI.dto.BookMessage;
import com.storyAi.story_AI.entity.Book;
import com.storyAi.story_AI.entity.Notification;
import com.storyAi.story_AI.repository.BookRepository;
import com.storyAi.story_AI.repository.NotificationRepository;
@Component
public class BookConsumer {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired 
    private BookRepository bookRepository;
    @KafkaListener(topics = "new-books", groupId = "book-group", containerFactory = "bookKafkaListenerContainerFactory")
    public void consume(BookMessage bookMessage) throws Exception {
    	Book book=bookRepository.findById(bookMessage.getId()).orElseThrow(()-> new Exception("Book not fount"));
  	messagingTemplate.convertAndSend("/topic/new-books", bookMessage);
  	Notification notification=new Notification();
  	notification.setCreatedAt(LocalDateTime.now());
  	notification.setBook(book);
  	notification.setStatus(NotificationStatus.BOOK);
  	notificationRepository.save(notification);
    	 System.out.print(" Message received and sent via WebSocket");
    	  /*try {
    	        String jsonMessage = objectMapper.writeValueAsString(bookMessage);
    	        messagingTemplate.convertAndSend("/topic/new-books", jsonMessage);
    	        System.out.println(" Message sent via WebSocket: " + jsonMessage);
    	    } catch (Exception e) {
    	        System.err.println(" Error converting to JSON: " + e.getMessage());
    	    }*/
    }
}
