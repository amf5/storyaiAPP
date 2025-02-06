package com.storyAi.story_AI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;



import com.storyAi.story_AI.dto.ActivateAccount;
import com.storyAi.story_AI.dto.ChangeMyPassword;
import com.storyAi.story_AI.dto.Login;
import com.storyAi.story_AI.dto.Signup;
import com.storyAi.story_AI.entity.CustomUserDetails;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/storyai/auth")
public class AuthController {
@Autowired
private TokenUtil tokenUtil;
@Autowired
private UserRepository repository;
    @Autowired
    private AuthService authService;
  

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) throws Exception {
        return authService.login(login);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Signup signup) {
        return authService.signup(signup);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccount activateAccount) {
        return authService.activateAccount(activateAccount);
    }

    @PostMapping("/resentcode")
    public ResponseEntity<?> resent(@RequestBody String email) throws MessagingException {
        return authService.resentCode(email);
        }

   @PostMapping("/logout")
   public ResponseEntity<?> logout(HttpServletRequest request, HttpSession session) {
      return authService.logout(request, session);
   }

   @GetMapping("/provider")
   public ResponseEntity<?> loginWithProvider(
           @RequestParam(value = "error", required = false) String error) {
              if (error != null) {
           return ResponseEntity.status(400).body("⚠️ Login error: " + error);
       }

       
       try {
           return authService.loginWithProvider();
       } catch (Exception e) {
           return ResponseEntity.status(500).body("❌ An error occurred during login: " + e.getMessage());
       }
   }

   @PostMapping("/forgot")
   public ResponseEntity<?> forgot(@RequestBody String email) {
      return authService.forgotMyPassword(email);
   }
   @PostMapping("/change")
   public ResponseEntity<?> change(@RequestBody ChangeMyPassword changeMyPassword) {
      return authService.changeMyPassword(changeMyPassword);
   }
@GetMapping()
public String getMethodName() {
    return "welcome";
}

@GetMapping("/good")
public String getMeيthodName() {
    try {
        // التوكين المرسل
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJTdGF0dXMiOnRydWUsIlJvbGVzIjpbIlVTRVIiXSwidXNlck5hbWUiOiJBaG1lZCB3YWxpZCIsInN1YiI6ImFobWVkd2FsaWRhbWluNjQ4QGdtYWlsLmNvbSIsImlhdCI6MTczODgxMDg0OCwiZXhwIjoxNzM4ODI4ODQ4fQ.UU82J9fGvjiOIP1CNxgyTIUGwi2g1Wns9dXzk6SqTz4"
      ;  
        // استخراج الإيميل من التوكين
        String email;
        try {
            email = tokenUtil.getEmailFromToken(token);
            System.out.println("Extracted Email: " + email);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting email from token: " + e.getMessage(), e);
        }

        try {
            String token1=repository.findTokenByEmail(email);
            
            System.out.println("Extract token"+token1);
            System.out.println("11111"+token1.equals(token));
        } catch (Exception e) {
            throw new RuntimeException("Error extracting email from token: " + e.getMessage(), e);
        }

        // التحقق من وجود المستخدم بناءً على الإيميل
        Optional<User> user;
        try {
            user = repository.findByEmail(email);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found for email: " + email);
            }
            System.out.println("User found: " + user.get().toString());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user from repository: " + e.getMessage(), e);
        }

        // التحقق من صحة التوكين باستخدام UserDetails
        boolean isValid;
        try {
            isValid = tokenUtil.validateToken(token, new CustomUserDetails(user.get()));
            System.out.println("Token is valid: " + isValid);
        } catch (Exception e) {
            throw new RuntimeException("Error validating token: " + e.getMessage(), e);
        }

        return "authentication";

    } catch (Exception e) {
        e.printStackTrace(); // طباعة تفاصيل الخطأ
        return "Error occurred: " + e.getMessage();
    }
}

   
}










































