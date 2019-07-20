package com.luv2code.springsecurity.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
			.antMatchers("/").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
			.antMatchers("/leaders/**").hasRole("MANAGER")
			.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		 .formLogin().loginPage("/login")
		.and()
		.logout().permitAll();

	}
	
	public CustomAuthenticationFilter authenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
		/*
		 * UserBuilder users = User.withDefaultPasswordEncoder();
		 * auth.inMemoryAuthentication()
		 * .withUser(users.username("john").password("test123").roles("EMPLOYEE"))
		 * .withUser(users.username("mary").password("test123").roles("EMPLOYEE",
		 * "MANAGER"))
		 * .withUser(users.username("susan").password("test123").roles("EMPLOYEE",
		 * "ADMIN"));
		 */
	}

	public AuthenticationProvider authProvider() {
		CustomUserDetailsAuthenticationProvider provider = new CustomUserDetailsAuthenticationProvider(userDetailsService);
		return provider;
	}

	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
	}

	

}
