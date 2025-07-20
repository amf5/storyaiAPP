package com.storyAi.story_AI.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.dto.BookDto;
import com.storyAi.story_AI.entity.Book;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.entity.Video;
import com.storyAi.story_AI.kafka.BookProducer;
import com.storyAi.story_AI.repository.BookRepository;
import com.storyAi.story_AI.repository.UserRepository;

@Service
public class BookService {
private final BookRepository bookRepository;
private final UserRepository userRepository;
private final BookProducer bookProducer;
@Autowired
public BookService(BookRepository bookRepository,UserRepository userRepository,
		 BookProducer bookProducer) {
	this.bookRepository=bookRepository;
	this.userRepository=userRepository;
	this.bookProducer=bookProducer;
}

public Book createBook(Long userId,BookDto bookDto) throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	Book book=new Book();
	book.setAbout(bookDto.getAbout());
	book.setLanguage(bookDto.getLanguage());
	book.setNameBook(bookDto.getNameBook());
	book.setOuthor(bookDto.getOuthor());
	book.setCoverImage(bookDto.getCoverImage());
	book.setIntroduction(bookDto.getIntroduction());
	book.setUser(user);
	book.setPage(bookDto.getPage());
	book.setCreatedBook(LocalDateTime.now());
	Book bookResponse=bookRepository.save(book) ;
	bookResponse.setUser(null);
	return bookResponse;
}

public String doOrCancelPublishingBook(Long userId,Long bookId, boolean published) throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	Book book=bookRepository.findById(bookId).orElseThrow(()->new Exception ("book not found"));
	String publish="";
	if(book.getUser().getId().equals(userId)) {
		publish=(published)?"publish":"unpublish";
		if(book.isPublished()==published) {
		return "you have did"+publish  +"before";
	}
		
   book.setPublished(published);
   if(published==true) {
   bookProducer.sendBookMessage(book.getBookId(), book.getAbout());}
   bookRepository.save(book);	
	return "Done"+publish;}
	throw new Exception("you dont have this book you can't publish or unpublish");
}

public List<BookDto> getPublishedBooks(){
	return bookRepository.findByPublished(true);
	
}
public List<BookDto> getMyBooks(Long userId) throws Exception{
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	return bookRepository.findMyBooks(userId);
}


public List<Book> filterAbout(String about){
	return bookRepository.findByAboutLike(about);
}

public List<Book> filterOuthor(String outhor){
	return bookRepository.findByOuthorLike(outhor);
}

public List<Book> filterLanguage(String language){
	return bookRepository.findByLanguageLike(language);
}

public List<Book> SearchBook(String search){
	return bookRepository.searchByKeyword(search);
}


















}
