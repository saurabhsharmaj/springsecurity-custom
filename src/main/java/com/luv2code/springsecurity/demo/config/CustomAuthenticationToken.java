package com.luv2code.springsecurity.demo.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String role;

    public CustomAuthenticationToken(Object principal, Object credentials, String role) {
        super(principal, credentials);
        this.role = role;
        super.setAuthenticated(false);
    }

    public CustomAuthenticationToken(Object principal, Object credentials, String role, 
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.role = role;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getRole() {
        return this.role;
    }
}
