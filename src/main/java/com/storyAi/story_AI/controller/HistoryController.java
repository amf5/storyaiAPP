package com.storyAi.story_AI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.HistoryService;
@RestController
@RequestMapping("/user/history")
public class HistoryController {
private final HistoryService historyService;
private final TokenUtil tokenUtil;
@Autowired
public HistoryController(HistoryService historyService,TokenUtil tokenUtil) {
	this.historyService=historyService;
	this.tokenUtil=tokenUtil;
}


@PostMapping("/do")
public ResponseEntity<?> doHistory(@RequestParam("userId")Long userId,
		@RequestParam("bookId")Long bookId
		,@RequestHeader("Authorization")String jwt) throws Exception{
Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
	   throw new Exception("Error....you dont have this id");
}
 return new ResponseEntity(historyService.doHistory(userIdCompare, bookId),HttpStatus.CREATED);
	
}

@GetMapping("/{userId}")
public ResponseEntity<?>getHistories(@PathVariable Long userId
		,@RequestHeader("Authorization") String jwt
		) throws Exception
{
	Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	}
	return ResponseEntity.ok(historyService.getHistories(userId));
}






}



