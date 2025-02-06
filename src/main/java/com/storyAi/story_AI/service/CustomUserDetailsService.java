package com.storyAi.story_AI.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.storyAi.story_AI.entity.CustomUserDetails;
import com.storyAi.story_AI.entity.User;
import com.storyAi.story_AI.repository.UserRepository;

import jakarta.validation.constraints.Email;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository repository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        User user = userOptional.get();
        return new CustomUserDetails(user);
    }

}
