package com.gcu.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.BlogModel;

//import lombok.extern.slf4j.Slf4j;

//This is where all business logic regarding blogs will go
@Service
public class BlogBusinessService implements BlogBusinessInterface {
	
	@Autowired
	private DataAccessInterface<BlogModel> service;
	
	/**
	* 	used to create a blog
	*	@param: BlogModel - BlogModel provided by user
	*	@return type: boolean - (true if new row was added)
	*/
	@Override
	public boolean insert(BlogModel blog) {
		//log.info("Enter BlogBusinessService.insert()");
		
		//initialize boolean variable
		boolean isCreated = false;
		
		//store int returned by create in rowInserted
		int rowInserted = service.create(blog);
		
		//if one result was found
		if(rowInserted == 1) {
			//user logged in successfully
			//log.info("Exit BlogBusinessService.insert() with one new row");
			isCreated = true;
		}
		
		//return false if user was not found successfully 
		//log.info("Exit BlogBusinessService.insert() with no new row");
		return isCreated;
	}
	
	
	/**
	 * Used to find the individual blog by a specified id
	 * returns the blogmodel
	 * @param int id
	 * @return BlogModel
	 * **/
	public BlogModel findById(int id) {
		//log.info("Entering BlogBusinessService.findById()");
		
		//store blog found by id in myBlog
		BlogModel myBlog = service.findById(id);
		
		if(myBlog == null) {
			//log.info("Exit BlogBusinessService.findById() with null blog");
			
			//return empty myBlog
			return myBlog;
		}else {
			//log.info("Exit BlogBusinessService.findById() with a blog");
			
			//return a blog
			return myBlog;
		}
	}
	
	/**used to get all blogs
	 *@param: none
	 * @return type: List<BlogModel> - ArrayList holding all blogs in the db
	 * **/
	@Override
	public List<BlogModel> findAll() {
		//returns the list of all blogs
		return service.findAll();
	}
	
	//used to get all blogs
	//param: none
	//return type: List<BlogModel> - ArrayList holding all blogs owned by a specific user in the db
	@Override
	public List<BlogModel> findAllById(int id) {
		//returns a list of blogs created by one user
		return service.findAllById(id);
	}
	
	/**
	 * used to edit a blog
	 * @param: none
	 * @return type: boolean - used to determine whether or not a blog was updated successfully 
	**/
	@Override
	public boolean edit(BlogModel blog) {
		//log.info("Enter BlogBusinessService.edit()");

		// initialize boolean variable
		boolean isUpdated = false;

		// store int returned by create in rowInserted
		int rowUpdated = service.update(blog);

		// if one result was found
		if (rowUpdated == 1) {
			// user logged in successfully
			//log.info("Exit BlogBusinessService.edit() with one row affected");
			isUpdated = true;
		}
		
		//log.info("Exit BlogBusinessService.edit() with no row affected");
		
		//return 0 if blog was not updated correctly
		return isUpdated;
	}
	
	/**
	 * 	used to remove a blog
	 * @param none
	 * @return type: boolean - used to determine whether or not a blog was deleted
	 * **/
	@Override
	public boolean remove(BlogModel blog) {
		//log.info("Enter BlogBusinessService.remove()");
		
		//initialize boolean variable
		boolean isDeleted = false;
		
		//store int returned by delete in rowDeleted
		int rowDeleted = service.delete(blog);
		
		//if one result was found
		if(rowDeleted == 1) {
			//log.info("Exit BlogBusinessService.remove() with one row affected");
			
			//blog was deleted successfully
			isDeleted = true;
		}
		
		//log.info("Exit BlogBusinessService.remove() with no row affected");
		
		//blog was not deleted successfully
		return isDeleted;
	}
}