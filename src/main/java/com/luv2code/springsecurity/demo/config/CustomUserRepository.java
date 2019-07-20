package com.luv2code.springsecurity.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class CustomUserRepository implements UserRepository {

	
	List<User> users = new ArrayList<User>();
	
	
	public CustomUserRepository(){
		
		Collection<GrantedAuthority> empAuthorities = new ArrayList<>();
		empAuthorities.add(getAuthority("EMPLOYEE"));
		Collection<GrantedAuthority> managerAuthorities = new ArrayList<>();
		managerAuthorities.add(getAuthority("MANAGER"));
		Collection<GrantedAuthority> admiAuthorities = new ArrayList<>();
		admiAuthorities.add(getAuthority("ADMIN"));
		Collection<GrantedAuthority> ja = new ArrayList<>();
		ja.addAll(empAuthorities);
		Collection<GrantedAuthority> mr = new ArrayList<>();
		mr.addAll(empAuthorities);
		mr.addAll(managerAuthorities);
		Collection<GrantedAuthority> su = new ArrayList<>();
		su.addAll(empAuthorities);
		su.addAll(admiAuthorities);
		
		users.add(new User("john", "EMPLOYEE", "test123", true, true, true, true,ja ));
		users.add(new User("mary", "MANAGER", "test123", true, true, true, true,mr ));
		users.add(new User("susan", "ADMIN", "test123", true, true, true, true,su ));
	}
	@Override
	public User findUser(String username, String password, String role) {
		if(isUserAvaialble(username,password,role)) {
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(getAuthority(role));
			User user =  new User(username, role, 
					password, true, 
					true, true, true, authorities);
			return user;
		}else {
			return null;
		}
	}


	private boolean isUserAvaialble(String username, String password, String role) {
		for (User u : users) {
			if(u.getUsername().toString().equals(username) 
					&& u.getPassword().equals(password)
					&& u.getAuthorities().contains(getAuthority(role))) {
				return true;
			}
		
		}
		return false;
	}


	private GrantedAuthority getAuthority(String role) {		
		return new SimpleGrantedAuthority("ROLE_"+role.toUpperCase());
	}
}
