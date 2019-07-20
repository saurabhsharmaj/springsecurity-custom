package com.luv2code.springsecurity.demo.config;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

    UserDetails validateUserDetails(String username, String password, String role);

}
