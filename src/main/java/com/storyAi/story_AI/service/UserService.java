package com.storyAi.story_AI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.dto.BookDto;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.BookRepository;
import com.storyAi.story_AI.repository.UserRepository;

@Service
public class UserService  {
	
  
   private final UserRepository userRepository;
   private final BookRepository bookRepository;
   @Autowired
   public UserService(UserRepository userRepository,BookRepository bookRepository) {
	  this.userRepository=userRepository;
	  this.bookRepository=bookRepository;
   }
   
   public String getUserName(Long userId) throws Exception {
	   User  user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found")) ;
	   return user.getFirstName();
   }
   
   public List<BookDto> getRecommendation(Long userId) throws Exception{
	   User  user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found")) ;
	   return bookRepository.findAvailableBooks(userId);
   };
   
   
}

