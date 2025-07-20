package com.storyAi.story_AI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.entity.Rate;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.RateRepository;
import com.storyAi.story_AI.repository.UserRepository;

@Service
public class RateService {
@Autowired 
private RateRepository rateRepository;
@Autowired 
private UserRepository userRepository;





public void doRate(Rate rate, Long userId) throws Exception {
	
	User user=userRepository.findById(userId).orElseThrow(()->new Exception("user not found"));
	
	Rate rate2=rateRepository.findByUserId(userId);
	Rate newRate=rate2;
	if(newRate==null) {
		
		newRate=new Rate();
		newRate.setNum(rate.getNum());
		newRate.setUser(user);
		newRate.setDescription(rate.getDescription());
	}
rateRepository.save(newRate);
	
}
}
