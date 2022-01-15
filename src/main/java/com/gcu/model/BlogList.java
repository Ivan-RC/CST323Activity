package com.gcu.model;

import java.util.ArrayList;
import java.util.List;

//This model will be utilized to hold a list of blogs from the database 
public class BlogList {
	
	//list of blogs
	private List<BlogModel> blogs = new ArrayList<BlogModel>();
	
	//getter and setter
    public List<BlogModel> getBlogs(){
        return this.blogs;
    }

    public void setBlogs(List<BlogModel> Blogs) {
        this.blogs = Blogs;
    }
}
