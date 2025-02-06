package com.storyAi.story_AI.aspect;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.storyAi.story_AI.repository.UserRepository;

@Component
@Aspect
public class CheckCode {
	 @Autowired
	    private UserRepository userRepository;

	    private final AtomicBoolean isCheckingEnabled = new AtomicBoolean(false);
	    private LocalDateTime startTime;

	   
	    @AfterReturning("execution(* com.storyAi.story_AI.service.EmailService.sendVerificationEmail(..))")
	    public void enableVerificationCodeChecking() {
	        isCheckingEnabled.set(true);
	        startTime = LocalDateTime.now();
	    }

	    @Scheduled(fixedRate = 60000)
	    public void clearExpiredCodes() {
	        if (!isCheckingEnabled.get()) return;

	        
	        if (startTime != null && LocalDateTime.now().isAfter(startTime.plusMinutes(6))) {
	            isCheckingEnabled.set(false);
	            return;
	        }

	       
	        userRepository.clearExpiredVerificationCodes();
	    }
}
