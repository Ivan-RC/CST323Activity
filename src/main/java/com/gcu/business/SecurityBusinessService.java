package com.gcu.business;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.LoginModel;

//import lombok.extern.slf4j.Slf4j;

//This is where all business logic regarding security will go
@Service
public class SecurityBusinessService implements SecurityBusinessInterface{
	
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
}