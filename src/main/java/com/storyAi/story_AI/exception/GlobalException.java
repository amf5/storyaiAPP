package com.storyAi.story_AI.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.storyAi.story_AI.dto.ErrorDto;


import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.UNAUTHORIZED .value());
        errorResponse.put("error", "Internal Server Error");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	
	@ExceptionHandler(ProducerException.class)
	public ResponseEntity<ErrorDto>ProducerException(ProducerException ex){
	ErrorDto errorDto=new ErrorDto();
	errorDto.setError("Producer Exception for message");
	
	return new ResponseEntity(errorDto,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto>handleMethodArgumentValidException(MethodArgumentNotValidException ex){
		

	    ErrorDto errorDto = new ErrorDto();

	    BindingResult bindingResult = ex.getBindingResult();
	    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		  String message = fieldErrors.stream()
		            .findFirst()
		            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
		            .orElse("Validation error");

		    errorDto.setError(message);

		    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public  ResponseEntity<ErrorDto>handleContraintValidation(ConstraintViolationException ex)
	{
		ErrorDto errorDto=new ErrorDto();
		String errorMessage=ex.getConstraintViolations().stream().findFirst().
		map(violation-> violation.getPropertyPath()+": "+violation.getMessage()).orElse("Constraint violation occurred");
		errorDto.setError(errorMessage);
		
		return new ResponseEntity(errorDto,HttpStatus.BAD_REQUEST);
		
	}
}
