package com.storyAi.story_AI.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	private final UserService userService;
	private final TokenUtil tokenUtil;
	@Autowired
	public UserController(UserService userService,TokenUtil tokenUtil) {
		
		this.userService=userService;
		this.tokenUtil=tokenUtil;
	}
@GetMapping("/{id}")
public ResponseEntity<?>getName(@PathVariable Long id,@RequestHeader("Authorization")String jwt) throws Exception{
	 Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	
	   if(!id.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	   }
	   return ResponseEntity.ok(userService.getUserName(id));
	
	
}

@GetMapping("/recommendation/{userId}")
public ResponseEntity<?> getRecommendation(@PathVariable Long userId,@RequestHeader("Authorization")String jwt) throws Exception{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	   
	   if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	   }
	   return ResponseEntity.ok(userService.getRecommendation(userIdCompare));
}

}
