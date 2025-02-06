package com.storyAi.story_AI.entity;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
private User user;

public CustomUserDetails(User user) {
	
	this.user=user;
}





	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getFirstName()+" "+user.getLastName();
	}
	
	public String getFirstname() {
		return user.getFirstName();
	}
	
	public String getLastname() {
		return user.getLastName();
	}
	public String getEmail() {
		return user.getEmail();
	}

	@Override
		public boolean isEnabled() {
			
			return user.isStatus();
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
