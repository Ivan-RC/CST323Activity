package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.LoginModel;

//import lombok.extern.slf4j.Slf4j;

//This is where all business logic regarding security will go
@Service
public class SecurityBusinessService implements UserDetailsService{
	
	@Autowired
	private DataAccessInterface<LoginModel> service;
	
	
	@Autowired
	private HttpSession session;

	/**
	 * used to verify successful user login
	 * @param LoginModel - credentials entered by user
	 * @return type: boolean (false if unsuccessful/exception, true otherwise)
	 * **/
	public boolean authenticate(LoginModel credentials) {
		//log.info("Enter SecurityBusinessService.authenticate()");
		
		//initialize boolean variable
		boolean isAuthenticated = false;
		
		//store int returned by findByModel in results
		int results = service.findByUsernameAndPassword(credentials);
		
		//if one result was found
		if(results == 1) {
			//user logged in successfully
			//log.info("Exit SecurityBusinessService.authenticate() with successful login");
			isAuthenticated = true;
		}
		//user did not login successfully
		//log.info("Exit SecurityBusinessService.authenticate() with unsuccessful login");
		return isAuthenticated;
	}

	/**
	 * This method is used to find the user by their username
	 * @param username - used to find user by username
	 * @return UserDetails (user if found, exception if not found
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginModel user = service.findByUsername(username);
		if(user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
			session.setAttribute("USER_ID", user.getUserId());
			System.out.println(encoded);
			
			return new User(user.getUsername(), user.getPassword(), authorities);
			
		}else {
			throw new UsernameNotFoundException("Username not found");
		}
	}
}