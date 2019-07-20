package com.luv2code.springsecurity.demo.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	CustomUserDetailsService userDetailsService;
	
	public CustomUserDetailsAuthenticationProvider(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		CustomAuthenticationToken auth = (CustomAuthenticationToken) authentication;		
		String password=auth.getCredentials().toString();
		String role = auth.getRole();
		UserDetails user;
		System.out.println("username:"+username+" password:"+password+" role:"+role);
		if (username == null|| password==null || role == null) {
			return null;
		} else {
			user = userDetailsService.validateUserDetails(username,password, role);			
			//UserDetails user = new User(username, role,password, true, true, true, true, authorities);
			return user;
		}
		
	}
}
