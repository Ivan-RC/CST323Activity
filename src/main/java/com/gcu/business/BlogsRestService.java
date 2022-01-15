package com.gcu.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.model.BlogModel;

//This is a REST service used to get all blogs, and a specific blog by id
@RestController
@RequestMapping("/service")
public class BlogsRestService {
	
	@Autowired
	BlogBusinessInterface service;
	
	/**
	 * This method returns blogs formatted in a JSON string
	 * @param None
	 * @Return List<BlogModel> - list of blogs to be returned 
	 **/
	@GetMapping(path="/getBlogs", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<BlogModel> getBlogs(){
		//call findAll() method in serivce and return what comes back
		return service.findAll();
	}
	/**
	 * This method returns a individual blog formatted in a JSON string
	 * @param BlogModel blog with desired search id
	 * @Return BlogModel - a specific blog in the db will be returned
	 **/
	@RequestMapping(path="/getBlogById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public BlogModel getBlogById(@PathVariable int id){
		//call findById() method in service and return what comes back
		return service.findById(id);
	}
}