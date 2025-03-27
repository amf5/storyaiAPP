package com.storyAi.story_AI.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.dto.BookDto;
import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.BookService;
import com.storyAi.story_AI.service.CloudinaryService;

@RestController
@RequestMapping("/user/books")
public class BookController {
private final BookService bookService;
private final ObjectMapper objectMapper;
private final CloudinaryService cloudinaryService;
private final TokenUtil tokenUtil;
@Autowired
public BookController(BookService bookService,ObjectMapper objectMapper,CloudinaryService cloudinaryService,TokenUtil tokenUtil) {
	this.bookService=bookService;
	this.objectMapper=objectMapper;
	this.cloudinaryService=cloudinaryService;
	this.tokenUtil=tokenUtil;
}


@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> createBook(
        @RequestPart("data") String bookRequestJson,
        @RequestPart("coverImage") MultipartFile coverImage,
        @RequestParam("userId") Long userId,
        @RequestHeader("Authorization")String jwt) throws Exception {
   Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
   
   if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
	   throw new Exception("Error....you dont have this id");
   }
   
    BookDto bookDto=objectMapper.readValue(bookRequestJson, BookDto.class);
    String imageUrl = cloudinaryService.uploadFile(coverImage);

    return new  ResponseEntity(bookService.createBook(userIdCompare, bookDto),HttpStatus.ACCEPTED);
}


@GetMapping("/published")
public ResponseEntity<?>getPublishedBook(){
	
	return ResponseEntity.ok(bookService.getPublishedBooks());
}

@PatchMapping("/publish_unpublish")
public ResponseEntity<?>doOrCancelPublishBook(@RequestParam("bookId")Long bookId,
		@RequestHeader("Authorization")String jwt,@RequestParam("published") boolean published) throws Exception{
	Long userId=tokenUtil.getIdFromBearerJwt(jwt);
	   if(userId.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	   }
	  
	  return new ResponseEntity(bookService.doOrCancelPublishingBook(userId, bookId, published),HttpStatus.ACCEPTED);
}
@GetMapping("/{userId}")
public ResponseEntity<?>getMyBooks(@PathVariable Long userId,@RequestHeader("Authorization")String jwt) throws Exception{
	 Long userIdCompare=tokenUtil.getIdFromBearerJwt(jwt);
	   
	   if(!userId.equals(userIdCompare)||userIdCompare.equals(null)) {
		   throw new Exception("Error....you dont have this id");
	   }
return new ResponseEntity(bookService.getMyBooks(userIdCompare),HttpStatus.OK);
}












}
