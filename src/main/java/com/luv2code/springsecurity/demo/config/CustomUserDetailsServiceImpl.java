package com.luv2code.springsecurity.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private UserRepository userRepository;
 
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails validateUserDetails(String username, String password, String role) {
		
        User user = userRepository.findUser(username,password, role);
        if (user == null) {
            throw new UsernameNotFoundException(
                String.format("Username not found for role, username=%s, domain=%s", 
                    username, role));
        }
        return user;
	}
}
