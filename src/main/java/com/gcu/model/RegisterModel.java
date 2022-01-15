package com.gcu.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//This model will be used to save the users profile(credentials, email, etc.) to the database
public class RegisterModel {
	
	//each field has annotations that will prevent anyone from enter garbage into our database
    @NotNull(message="First name is a required field")
    @Size(min=2, max=45, message="First name must be between 2 and 45 characters")
    private String firstName;
    
    @NotNull(message="Last name is a required field")
    @Size(min=2, max=45, message="Last name must be between 2 and 45 characters")
    private String lastName;
    
    @NotNull(message="Email is a required field")
    @Size(min=5, max=45, message="Email must be between 5 and 45 characters")
    private String email;
    
    @Valid
    private LoginModel credentials;
    
    //non default constructor
	public RegisterModel(String firstName, String lastName, String email, LoginModel credentials) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.credentials = credentials;
	}
	
	//default constructor 
	public RegisterModel() {
		super();
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.credentials = new LoginModel();
	}
	
	//getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginModel getCredentials() {
		return credentials;
	}

	public void setCredentials(LoginModel credentials) {
		this.credentials = credentials;
	}
}