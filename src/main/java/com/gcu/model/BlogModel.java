package com.gcu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//This model will be utilized to save blog information to the database
public class BlogModel {
	
	private int id;
	
	//each field has annotations that will prevent anyone from enter garbage into our database
	@NotNull(message="Title is a required field")
    @Size(min=5, max=45, message="Title must be between 5 and 45 characters")
	private String title;
	
	@NotNull(message="Description is a required field")
    @Size(min=5, max=500, message="Description must be between 5 and 500 characters")
	private String description;
	
	//used to associate a user with a blog they have created
	private int userId;
	
	//default constructor
	public BlogModel() {
		super();
		this.id = 0;
		this.title = "";
		this.description = "";
		this.userId = 0;
	}

	//Non default constructor
	public BlogModel(int id, String title, String description, int userId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.userId = userId;
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}