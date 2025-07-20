package com.storyAi.story_AI.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storyAi.story_AI.entity.Rate;
import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.RateService;


@RestController
@RequestMapping("/user/rate")
public class RateController {
	
private final RateService rateService;
private final TokenUtil tokenUtil;

public RateController(RateService rateService,TokenUtil tokenUtil ) {
	this.tokenUtil=tokenUtil;
	this.rateService =rateService;
	
}

@PostMapping()
public ResponseEntity<Void>doRate(@RequestBody Rate rate,@RequestParam Long userId, @RequestHeader("Authorization")String jwt) throws Exception{
	
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userIdCompare.equals(userId)) {
		
		throw new Exception("user not found with this id: "+userId);
	}
	try {
		rateService.doRate(rate, userIdCompare);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();

	} catch (Exception e) {
		throw new Exception(e.getMessage());
	}
	
}
}
