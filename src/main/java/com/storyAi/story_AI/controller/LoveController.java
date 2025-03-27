package com.storyAi.story_AI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.LoveService;

@RestController
@RequestMapping("user/loves")
public class LoveController {
private final LoveService loveService;
private final TokenUtil tokenUtil;
@Autowired
public LoveController(LoveService loveService,TokenUtil tokenUtil) {
	this.loveService =loveService;
	this.tokenUtil=tokenUtil;
}

@PostMapping("/do")
public ResponseEntity<?>doLove(@RequestParam("userId")Long userId,
		@RequestParam("bookId")Long bookId,
		@RequestHeader("Authorization")String jwt) throws Exception{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	}
	
	return new ResponseEntity(loveService.doLove(userId, bookId),HttpStatus.CREATED);
}
@DeleteMapping("/cancel")
public ResponseEntity<?>cancelLove(@RequestParam("userId")Long userId,
		@RequestParam("bookId")Long bookId,
		@RequestHeader("Authorization")String jwt) throws Exception{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	}
	
	return new ResponseEntity(loveService.CancelLove(userId, bookId),HttpStatus.ACCEPTED);
}
	@GetMapping("{userId}")
	public ResponseEntity<?>getLoves(@PathVariable Long userId,@RequestHeader("Authorization")String jwt) throws Exception{
		Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
		if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
			   throw new Exception("Error....you dont have this id");
		}
		return ResponseEntity.ok(loveService.getMyLoves(userId));
	}
}
