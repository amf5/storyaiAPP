package com.storyAi.story_AI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.dto.LoveResponse;
import com.storyAi.story_AI.entity.Book;
import com.storyAi.story_AI.entity.Love;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.BookRepository;
import com.storyAi.story_AI.repository.LoveRepository;
import com.storyAi.story_AI.repository.UserRepository;

@Service
public class LoveService {
	
	private final LoveRepository loveRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	@Autowired
	public LoveService(LoveRepository loveRepository,UserRepository userRepository,BookRepository bookRepository) {
		this.loveRepository=loveRepository;
		this.userRepository=userRepository;
		this.bookRepository=bookRepository;
			}
public String doLove(Long userId, Long bookId) throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	Book book=bookRepository.findById(bookId).orElseThrow(()->new Exception("book not found"));
	if(!book.isPublished()) {
		if(!book.isPublished()) {
			throw new Exception("book not found");
		}
		
	}
	if(loveRepository.findByUserIdAndBook_BookId(userId, bookId)==null) 
	{
	Love love=new Love();
	love.setLovedAt(LocalDateTime.now());
	love.setUser(user);
	love.setBook(book);
	loveRepository.save(love);
	
	return "done";}
	return "you have aready done love before";
	
}
public String CancelLove(Long userId,Long bookId)throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	Book book=bookRepository.findById(bookId).orElseThrow(()->new Exception("book not found"));
	if(!book.getUser().getId().equals(userId)) {
		if(!book.isPublished()) {
			throw new Exception("book not found");
		}
		
	}
	Love love=loveRepository.findByUserIdAndBook_BookId(userId, bookId);
	if(love==null) {
		return "there is no love for this book";
	}
	loveRepository.delete(love);
	return "donecanceled";
	
	
}
 public List<LoveResponse> getMyLoves(Long userId) throws Exception{
	 User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found")); 
	 
	 return loveRepository.getLoves(userId);
 }













}
