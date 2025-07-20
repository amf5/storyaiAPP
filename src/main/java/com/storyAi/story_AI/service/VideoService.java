package com.storyAi.story_AI.service;








import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;


import com.storyAi.story_AI.dto.VideoResponse;
import com.storyAi.story_AI.entity.Book;
import com.storyAi.story_AI.entity.History;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.entity.Video;
import com.storyAi.story_AI.kafka.VideoProducer;
import com.storyAi.story_AI.mapper.VideoResponseMapper;
import com.storyAi.story_AI.repository.HistoryRepository;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.repository.VideoRepository;

import jakarta.transaction.Transactional;





@Service
public class VideoService {
	
	private final VideoRepository videoRepository;
	private final WebClient webClient;
	private final CloudinaryService cloudinaryService;
	private final UserRepository userRepository;
	private final HistoryRepository historyRepository;
private final VideoProducer videoProducer;
	@Autowired
	public VideoService(VideoRepository videoRepository,WebClient webClient,CloudinaryService cloudinaryService
			,UserRepository userRepository,HistoryRepository historyRepository, VideoProducer videoProducer) {
		this.videoRepository=videoRepository;
		this.webClient=webClient;
		this.cloudinaryService=cloudinaryService;
		this.userRepository = userRepository;
		this.historyRepository=historyRepository;
		this.videoProducer=videoProducer;
		}
	
	
	public String doAndDonotPublish(Long userId,Long videoId,boolean published) throws Exception {
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));	
	Video video=videoRepository.findById(videoId).orElseThrow(()->new Exception("video not found"));
	if(!user.getId().equals(video.getUser().getId())) {
		throw new Exception("video not found");
	}
	String publish;
	if(published==false) {
		
		publish="unpublished";
	}
	else {

		videoProducer.sendKafkaProducerVideo(video.getId(), video.getName(), video.getLinkVideo());
		publish="published";
	}
	if(video.isPublished()==published) {
		
		return "your video is aready"+publish+"before";
	}else {
		video.setPublished(published);
		return "your video is"+publish+"now";
		
	}
		
	}
	
	
	public List<Video>getPublishedVideos(){
		
		return videoRepository.findByPublished(true);
	}
	public List<Video>getMyVideos(Long userId) throws Exception{
		User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));	
		return videoRepository.findByUserId(userId);
	}
	@Transactional
	public Video saveVideoLinkToDatabase(String videoUrl, Long userId, String name, String about,String language) throws Exception {
	  
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new Exception("User not found"));

	    Video video = new Video();
	    video.setAbout(about);
	    video.setUser(user);
	    video.setLinkVideo(videoUrl);
	    video.setName(name);
	    video.setLanguage(language);
	    video.setCreator(user.getFirstName()+" "+user.getLastName());

	    try {
	      return  videoRepository.save(video);
	    } catch (Exception e) {
	        throw new Exception("Error saving video: " + e.getMessage(), e);
	    }
	}


public List<VideoResponse> MyVideos(Long userId) throws Exception{
User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));	
List<Video> videos=videoRepository.findByUserId(userId);
List<VideoResponse> response= videos.stream().map(video->{return VideoResponseMapper.toVideoResponse(video);}).collect(Collectors.toList());
return response;	
}


	/*public void doHistoryForVideos(Long userId,Long videoId ) throws Exception{
		User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));		
		Video video=videoRepository.findById(videoId).orElseThrow(()->new Exception("video not found"));
		History history=new History();
		Book book=new Book();
		book.setCoverImage(video.getLinkVideo());
		book.setBookId(videoId);
		book.setAbout(video.getAbout());
	    book.setIntroduction(video.getName()+" "+video.getAbout());
		book.setNameBook(video.getName());
		history.setBook(book);
		historyRepository.save(history);
		
		
		
	}*/
}


   
