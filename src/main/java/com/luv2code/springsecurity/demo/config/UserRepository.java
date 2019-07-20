package com.luv2code.springsecurity.demo.config;

public interface UserRepository {

	public User findUser(String username, String password, String role);
    
}
