package com.gcu.data;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import com.gcu.model.BlogModel;
import com.gcu.util.DatabaseException;
//import lombok.extern.slf4j.Slf4j;

/**
 * @author Anthony, Ivan
 * 12/12/2021
 * 
 */
@Service
public class BlogDataService implements DataAccessInterface<BlogModel> {

	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public BlogDataService(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	
	/**
	 * This method takes in an id and returns a blogmodel that is stored under that id
	 * 
	 * @param int Id - used to query for a blog by unique id
	 * @Return blogmodel - the blog found
	 * 
	 **/
	@Override
	public BlogModel findById(int id) {
		//log.info("Entering BlogDataService.findById()");
		
		//setting the statement
		String sql = "SELECT * FROM post WHERE `POST_ID` = ?";
		
		//initialize a new blog
		BlogModel myBlog = null;
		
		try {
			//get a users specific blog and store in result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			
			while(srs.next()) {
				//blog stores data from the database
				myBlog = new BlogModel(srs.getInt("POST_ID"), srs.getString("POST_TITLE"), srs.getString("POST_TEXT"), srs.getInt("USER_ID"));
			}
		}
		//catching any exceptions and logging
		catch(DatabaseException ex) {
			//log.error("Exit BlogDataService.findById() with exception - %s", ex);
			return null;
		}
		//log.info("Exit BlogDataService.findById() with users blog");
		
		//return blog
		return myBlog;
	}
	
	/**
	 * This method returns a list of blogModels
	 * The list contains all blogs stored in the database
	 * @param null - no parameter needed
	 * @return List<BlogModel> - used to return a list of blogs
	 * 
	 * **/
	@Override
	public List<BlogModel> findAll() {
		//log.info("Entering BlogDataService.findAll()");
		
		//setting the statement
		String sql = "SELECT * FROM post";
		
		//list to store all the retrieved blogs
		List<BlogModel> blogs = new ArrayList<BlogModel>();
		
		try {
			//execute
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			
			while(srs.next()) {
				//adding the blogs to the array
				blogs.add(new BlogModel(srs.getInt("POST_ID"), srs.getString("POST_TITLE"), srs.getString("POST_TEXT"), srs.getInt("USER_ID")));
			}
		}
		//catching any exceptions and logging
		catch(DatabaseException ex) {
			//log.error("Exit BlogDataService.findAll() with exception - %s", ex);
			return null;
		}
		//log.info("Exit BlogDataService.findAll() with blogs");
		
		//return blogs array
		return blogs;
	}
	
	/**
	 * 
	 * This method is used to find all given psots that a user has made
	 * <p>It will return a list of blogs</p>
	 * @param int id - used to find all blogs created by a specific user
	 * @return List<BlogModel> - returning all of a users blogs
	 * */
	@Override
	public List<BlogModel> findAllById(int id) {
		//log.info("Entering BlogDataService.findAllById()");
		
		//setting the statement
		String sql = "SELECT * FROM post WHERE `USER_ID` = ?";
		
		//list to store all the retrieved blogs
		List<BlogModel> myBlogs = new ArrayList<BlogModel>();
		
		try {
			//get all blogs
			//starting the uery
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			
			while(srs.next()) {
				//adding the blogs to the array
				myBlogs.add(new BlogModel(srs.getInt("POST_ID"), srs.getString("POST_TITLE"), srs.getString("POST_TEXT"), srs.getInt("USER_ID")));
			}
		}
		//catching any exceptions and logging
		catch(DatabaseException ex) {
			//log.error("Exit BlogDataService.findAllById() with exception - %s", ex);
			return null;
		}
		//log.info("Exit BlogDataService.findAllById() with users blogs");
		
		//return blogs array
		return myBlogs;
	}
	
	@Override
	public int findByUsernameAndPassword(BlogModel t) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Used to create a new blog, takes in a blogmodel
	 * returns an int for success or fail 
	 * @param BlogModel blog - BlogModel provided by user 
	 * @return int - used to determing whether or not a user was successfully created, or exception
	 * **/
	@Override
	public int create(BlogModel blog) {
		//log.info("Entering BlogDataService.create()");
		try {
			//setting up the sql statement
			String sql = "INSERT INTO post(POST_TITLE, POST_TEXT, USER_ID) VALUES(?,?,?)";
			
			//store number rows inserted in variable 
			int rowInserted = jdbcTemplateObject.update(sql, blog.getTitle(), blog.getDescription(), blog.getUserId());
			
			if(rowInserted == 0) {
				//if no row was inserted, return 0
				//log.info("Exit BlogDataService.create() with no new rows");
				return 0;
			}
		} catch(DatabaseException ex) {
			//exception handling 
			//log.error("Exit BlogDataService.create() with exception - %s", ex);
			return -1;
		}
		//if new row was inserted we return 1
		//log.info("Exit BlogDataService.create() with new row");
		return 1;
	}
	
	/**
	 * Method used to update a blog
	 * returns an int to indicate success or fail
	 * @param BlogModel t - updated blog provided by user
	 * @return int - used to determing whether or not a user was successfully created, or exception 
	 * 
	 * **/
	@Override
	public int update(BlogModel t) {
		
		//log.info("Entering BlogDataService.update()");

		try {
			//sql statement
			String sql = "UPDATE `post` SET `POST_TITLE` = ?, `POST_TEXT` = ? WHERE POST_ID = ?";
			
			//holds number of rows affected
			int rowInserted = jdbcTemplateObject.update(sql, t.getTitle(), t.getDescription(), t.getId());

			if (rowInserted == 0) {
				// if no row was inserted, return 0
				//log.info("Exit BlogDataService.update() with no row affected");
				return 0;
			}
		} catch (DatabaseException ex) {
			//log.error("Exit BlogDataService.update() with exception - %s", ex);
			return -1;
		}
		// if new row was inserted we return 1
		//log.info("Exit BlogDataService.update() with one row affected");
		return 1;
	}
	
	
	/**
	 * Method used to delete the blog using a blogmodel
	 * returns an int to indiacate success or fail
	 * @param BlogModel t - blog to be deleted provided by user
	 * @return int - used to determine whether or not a blog was successfully delete, or exception 
	 * **/
	@Override
	public int delete(BlogModel t) {
		//log.info("Entering BlogDataService.delete()");

		try {
			//Sql statement to delete 
			String sql = "DELETE FROM `post` WHERE `POST_ID` = ?";
			
			//holds number of rows affected
			int rowInserted = jdbcTemplateObject.update(sql, t.getId());

			if (rowInserted == 0) {
				// if no row was inserted, return 0
				//log.info("Exit BlogDataService.delete() with no row affected");
				return 0;
			}
		} catch (DatabaseException ex) {
			//exception handling
			//log.error("Exit BlogDataService.delete() with exception - %s", ex);
			return -1;
		}
		// if new row was inserted we return 1
		//log.info("Exit BlogDataService.delete() with one row affected");
		return 1;
	}
	
	@Override
	public BlogModel findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}