package com.storyAi.story_AI.service;





import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.dto.ActivateAccount;
import com.storyAi.story_AI.dto.AuthResponse;
import com.storyAi.story_AI.dto.ChangeMyPassword;
import com.storyAi.story_AI.dto.Login;

import com.storyAi.story_AI.dto.Signup;
import com.storyAi.story_AI.entity.CustomUserDetails;
import com.storyAi.story_AI.entity.Role;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.RoleRepository;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.security.TokenUtil;


import jakarta.mail.MessagingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Service
public class AuthService {
@Autowired
	private UserRepository repository;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private PasswordEncoder encoder;
@Autowired
EmailService emailService;

@Autowired
private TokenUtil jwtUtil;	
@Autowired
private OAuth2AuthorizedClientService authorizedClientService;
@Autowired
private RoleRepository roleRepository;
	
public ResponseEntity<?> login( Login login) throws Exception {
    try {
   Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(login.getUserName(),login.getPassword())
          );
    User user = repository.findByEmail(login.getUserName()).orElseThrow(() -> new RuntimeException("User not found"));
        CustomUserDetails userDetails =new CustomUserDetails(user);
        
        String jwt = jwtUtil.generateToken(userDetails);
user.setToken(jwt);
repository.save(user);
AuthResponse authResponse=new AuthResponse(user.getId(),jwt);
        return ResponseEntity.ok(authResponse); 

    } catch (Exception e) {
        return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
    }
}
	
public ResponseEntity<?>signup(Signup signup){
	Optional<User> user=repository.findByEmail(signup.getEmail());
	if(user.isPresent()) {
		
		return ResponseEntity.status(409).body("User already exists"); 
	}
	else if (repository.existsByEmail(signup.getEmail())) {
		return ResponseEntity.status(409).body("Email already exists"); 
	}
	else {
	
		Optional<Role> userRole = roleRepository.findByRoleName("USER");
Role role;
Set<Role> roles = new HashSet<>();
        if (!userRole.isPresent()) {
            role = new Role("USER", "write paragraphs to become films");
           //roleRepository.save(role);
           roles.add(role);
        }
        else
        {
        roles.add(userRole.get()); 
        }
	
	User newUser=new User(signup.getFirstName(),
			signup.getLastName(),signup.getEmail(),encoder.encode(signup.getPassword()),
			false,roles);
	newUser.setProvider("APP");
	
	try {
		emailService.sendVerificationEmail(newUser.getEmail(), newUser);
	} catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred while sending the verification email: " + e.getMessage());

	}
	 return ResponseEntity.ok("successful,,check your email now to To activate your account ");
	}
	
	
	
	
	
}


public ResponseEntity<?> activateAccount(ActivateAccount account){
	Optional<User> user=repository.findByEmail(account.getEmail());
	if(!user.isPresent())
	{
		
		return ResponseEntity.status(401).body("Authentication failed");
	}else {
		Duration duration = Duration.between(user.get().getVerificationCodeSentAt(), LocalDateTime.now());
		if(account.getCode().equals(user.get().getVerificationCode())
				&&duration.toMinutes() <= 5
				) {
			user.get().setVerificationCode(null);
			user.get().setStatus(true);
			user.get().setVerificationCodeSentAt(null);
		String token=	 jwtUtil.generateToken(new CustomUserDetails(user.get()));
		user.get().setToken(token);
		repository.save(user.get());
		AuthResponse authResponse=new AuthResponse(user.get().getId(),token);
        return ResponseEntity.ok(authResponse); 
		

		}else {
			
			return ResponseEntity.status(401).body("Invalid Code!");
		}
		
		
		
	}	
	
	
	
	
	
}


// resend code another times if fisrt code didnot go to user
public ResponseEntity<?> resentCode(String email) throws MessagingException{
	Optional<User> user=repository.findByEmail(email);
	

	if(!user.isPresent()) {
		
		return ResponseEntity.status(401).body("Authentication failed");
		
	}else {
		if(user.get().isStatus()) {
			return ResponseEntity.ok().body("your account is aready activated , go to login");
		}else {
		emailService.sendVerificationEmail(email, user.get());
		return ResponseEntity.ok().body("code is sent.");}
}


}

 // login by provider any why (Google,Facebook)
   public ResponseEntity<?> loginWithProvider() {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName()
            );
                if (authorizedClient != null) {
                Map<String, Object> userAttributes = oauthToken.getPrincipal().getAttributes();
                String email = (String) userAttributes.get("email");
                String name = (String) userAttributes.get("name");
                Optional<User> user = repository.findByEmail(email);
                String token=null;
                User current;
                if (user.isPresent()) {
                    current = user.get();
                    if(!user.get().getProvider().equalsIgnoreCase(authorizedClient.getClientRegistration().getClientName())) {
                    return ResponseEntity.ok().body("you have aready account by same email by account , go and login by your account");
                    }} else {
                    User newUser =createUser(email);
                  completeDetailsUserProvider(newUser, authorizedClient, userAttributes, name);
                   token=jwtUtil.generateToken(new CustomUserDetails(newUser)); 
                  newUser.setToken(token);
                  repository.save(newUser);
                    current = newUser;
                } 
                AuthResponse authResponse=new AuthResponse(current.getId(),token);
                return ResponseEntity.ok(authResponse); 
                
            } else {
                return ResponseEntity.status(401).body("Authorized client not found");
            }
        } else {
            return ResponseEntity.status(401).body("Authentication is not OAuth2-based");
        }
    } catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}

//creating newUser+ some details which share between user by google and user by face book
private User createUser(String email) {
	User newUser = new User();
	Optional<Role> userRole = roleRepository.findByRoleName("USER");
	Role role;
	        
	        if (!userRole.isPresent()) {
	            role = new Role("USER", "write paragraphs to become films");
	            roleRepository.save(role);
	        }

	        Set<Role> roles = new HashSet<>();
	        roles.add(userRole.get()); 

		
      newUser.setEmail(email);
      newUser.setPassword(encoder.encode(UUID.randomUUID().toString()));
      newUser.setRoles(roles);
      newUser.setStatus(true);
	
	return newUser;
	
}

//completeing details user provider as nature user (facebook or google)
 private void completeDetailsUserProvider(User newUser, OAuth2AuthorizedClient authorizedClient
		 , Map<String, Object> userAttributes,String name) {
	 if (authorizedClient.getClientRegistration().getClientName().equalsIgnoreCase("facebook")) {
         newUser.setFirstName(name);
         newUser.setProvider("facebook");
         newUser.setLastName(name);
     }

     if (authorizedClient.getClientRegistration().getClientName().equalsIgnoreCase("google")) {
         newUser.setFirstName((String) userAttributes.get("given_name"));
         newUser.setProvider("google");
         newUser.setLastName((String) userAttributes.get("family_name"));
     } 
	 
	 
	 
 }





 public ResponseEntity<?> logout(HttpServletRequest request, HttpSession session) {
     try {
         
         String token = jwtUtil.getTokenFromRequest(request);
         if (token == null || token.isEmpty()) {
             return ResponseEntity.status(400).body("Token is missing or invalid");
         }

         
         String email = jwtUtil.getEmailFromToken(token);
         if (email == null || email.isEmpty()) {
             return ResponseEntity.status(400).body("Invalid token: Unable to extract email");
         }

         Optional<User> user = repository.findByEmail(email);
         if (user.isPresent()) {
             
             user.get().setToken(null);
             repository.save(user.get()); 
         } else {
             return ResponseEntity.status(401).body("User not found or unauthorized");
         }

        
         if (session != null) {
             session.invalidate();
         }
         SecurityContextHolder.clearContext();

         return ResponseEntity.ok().body("Successful logout");
     } catch (Exception e) {
         
         return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
     }
 }



 
 
 public ResponseEntity<?> forgotMyPassword (String email){
	 Optional<User>user=repository.findByEmail(email);
	 if(user.isPresent()) {
		emailService.sendVerificationPassword(email, user.get());
		 return ResponseEntity.ok().body("email is sent ");
		 
	 }else {
		 return ResponseEntity.status(401).body("sorry! user not found check your email or create account if you dont have account");
		 
		 
	 }
	 
	}
 
 
 
 
 public ResponseEntity<?> changeMyPassword(ChangeMyPassword changeMyPassword){
	Optional<User> user=repository.findByEmail(changeMyPassword.getEmail());
	if(user.isPresent()) {
		
		user.get().setPassword(encoder.encode(changeMyPassword.getPassword()));	
		repository.save(user.get());
		return ResponseEntity.ok().body("successfull your password is changed");
		  
	}
	else {
		return ResponseEntity.status(401).body("sorry! user not found check your email or create account if you dont have account");
		 
	}
	 
	 
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 




}





	
	
	
	
	

