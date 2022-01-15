package com.gcu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//This model will be used to verify whether or not the user provided the correct credentials when logging in
public class LoginModel {
	
	//each field has annotations that will prevent anyone from enter garbage into our database
	//used to associate a user with a blog they have created
	private int userId;

	@NotNull(message="Username is a required field")
    @Size(min=2, max=45, message="Username must be between 5 and 45 characters")
    private String username;

    @NotNull(message="Password is a required field")
    @Size(min=5, max=45, message="Password must be between 5 and 45 characters")
    private String password;
    
    //Non default constructor
	public LoginModel(int userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	
	//default constructor
	public LoginModel() {
		super();
		this.userId = 0;
		this.username = "";
		this.password = "";
	}
	
	//getters and setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
