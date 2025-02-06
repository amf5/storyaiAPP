package com.storyAi.story_AI.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.security.TokenUtil;

import jakarta.mail.MessagingException;

@Service
public class EmailService {
	
	@Autowired
	private TokenUtil tokenUtil;
	  private static final String SUCCESS_SYMBOL = "✅";
	  private static final String FAILURE_SYMBOL = "❌";
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private UserRepository userRepo; 
    @Autowired
    private JavaMailSender mailSender; 
    private String generateActivationToken() {
        return UUID.randomUUID().toString(); 
    }
    
    public String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(10000); 
        return String.format("%04d", code); 
    }
    public void sendActivationEmail(String toEmail, String activationLink,User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("your_email@example.com");
        message.setSubject("Activate Your Account");

        String emailBody = "Welcome to "+user.getFirstName()+" "+user.getLastName()+"/n"+
        "To activate your account, please click the link below:"
    +"\nIf you didn't sign up for this account, please ignore this email.\nThank you,[Your App Name] Team".formatted(activationLink);

        message.setText(emailBody);
        mailSender.send(message);
    }

    
    public void sendVerificationEmail(String toEmail, User user)  {
       
        String verificationCode =generateVerificationCode();

   
        user.setVerificationCode(verificationCode); 
        user.setVerificationCodeSentAt(LocalDateTime.now());
        userRepo.save(user); 

       
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(senderEmail); 
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + verificationCode + "\n" +
                "Please enter this code to verify your email."); 

        mailSender.send(message);
    }
    
    
    
    public void sendVerificationPassword(String toEmail, User user)  {
        
        String verificationCode =generateVerificationCode();

   
        user.setVerificationCode(verificationCode); 
        user.setVerificationCodeSentAt(LocalDateTime.now());
        userRepo.save(user); 

       
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(senderEmail); 
        message.setSubject("Forgot My Password");
        message.setText("Your  code is: " + verificationCode + "\n" +
                "Please enter this code to change your password or to login by one step"); 

        mailSender.send(message);
    }
    
    
    
    
        public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(senderEmail);
        message.setSubject("Password Reset");
        message.setText("To reset your password, click the following link: " +
                "http://yourapp.com/reset-password?token=" + resetToken); 
        mailSender.send(message);
    }
        
        
        
        
        
        
      //  public void se
        /*private void sendVerificationEmail(String email, String code) {
        	
            String urlNo = "http://localhost:8081/storyai/auth/success?email=" + email;
            String urlYes = "http://localhost:8081/storyai/auth/block?email=" + email;
            String message = "Your verification code is: " + code + "\n\n"
                    + "If you initiated this registration, please enter the code in the application to activate your account.\n\n"
            		+"If you  initiate this registration, click here to activate the account:\n"
                    +SUCCESS_SYMBOL+" "+urlYes+"\n"
            		+ "If you did not initiate this registration, click here to cancel the account: \n"
                    + FAILURE_SYMBOL+" "+ urlNo;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Account Verification");
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        }*/
       /* private void sendVerificationEmail(String email, String ptoToken) {
    	    String urlVerify = "http://localhost:8080/api/auth/verify?token=" + ptoToken;
    	    String urlNotMe = "http://localhost:8080/api/auth/block?email=" + email;

    	    String message = "If you registered an account, please click the link below to verify your email:\n"
    	            + "🔑 " + urlVerify + "\n\n"
    	            + "If you did not register this account, click here to cancel the registration:\n"
    	            + "❌ " + urlNotMe;

    	    SimpleMailMessage mailMessage = new SimpleMailMessage();
    	    mailMessage.setTo(email);
    	    mailMessage.setSubject("Verify Your Account");
    	    mailMessage.setText(message);
    	    mailSender.send(mailMessage);
    	}*/
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
}
