package com.gcu.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.RegisterModel;

//import lombok.extern.slf4j.Slf4j;

//This class will hold business rules regarding registration
@Service
public class RegistrationBusinessService implements RegistrationBusinessInterface {
	
	@Autowired
	private DataAccessInterface<RegisterModel> service;
	
	/**
	 * used to register a new user
	 * @param RegisterModel - RegisterModel provided by user
	 * @return type: boolean - (true if new row was added)
	 * **/
	@Override
	public boolean insert(RegisterModel user) {
		//log.info("Enter RegistrationBusinessService.insert()");
		
		//initialize boolean variable
		boolean isCreated = false;
		
		//encrypt user password
		String encoded = new BCryptPasswordEncoder().encode(user.getCredentials().getPassword());
		user.getCredentials().setPassword(encoded);
		
		//store int returned by create in rowInserted
		int rowInserted = service.create(user);
		
		//if one result was added
		if(rowInserted == 1) {
			//user was registered successfully
			//log.info("Exit RegistrationBusinessService.insert() with one new row");
			
			//set isCreated to true and return
			isCreated = true;
		}
		
		//false if user was not registered successfully
		//log.info("Exit RegistrationBusinessService.insert() with no new row");
		return isCreated;
	}
}
