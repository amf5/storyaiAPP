package com.storyAi.story_AI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.dto.HistoryResponse;
import com.storyAi.story_AI.entity.Book;
import com.storyAi.story_AI.entity.History;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.BookRepository;
import com.storyAi.story_AI.repository.HistoryRepository;
import com.storyAi.story_AI.repository.UserRepository;

@Service
public class HistoryService {
private final BookRepository bookRepository;
private final UserRepository userRepository;
private final HistoryRepository historyRepository;
@Autowired
public HistoryService(BookRepository bookRepository,UserRepository userRepository,HistoryRepository historyRepository) {
	this.bookRepository=bookRepository;
	this.userRepository=userRepository;
	this.historyRepository=historyRepository;
}

public String doHistory(Long userId,Long bookId) throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	Book book=bookRepository.findById(bookId).orElseThrow(()->new Exception("book not found"));
	if(!book.isPublished()) {
		throw new Exception("you can't read this book");
	}
	History history=new History();
	history.setCreatedAt(LocalDateTime.now());
	history.setBook(book);
	history.setUser(user);
	historyRepository.save(history);
	return "done";
	
}
public List<HistoryResponse>getHistories(Long userId) throws Exception{
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	return historyRepository.getHistories(userId);
	}












}
