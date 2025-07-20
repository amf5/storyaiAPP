package com.storyAi.story_AI.exception;

public class ProducerException extends RuntimeException {

	public ProducerException(String message) {
		super(message);
		
	}
	
public ProducerException(String message,Throwable cause) {
		
		super(message,cause);
	}
	
public ProducerException(Throwable cause) {
	
	super(cause);
}
public ProducerException() {
	
	super();
}
public ProducerException(String message,Throwable cause,boolean enableSuppresion,boolean writableStackTrace) {
	
	super(message,cause,enableSuppresion, writableStackTrace);
}
}
