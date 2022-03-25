package com.gcu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.BlogBusinessInterface;
import com.gcu.model.BlogModel;
import lombok.extern.slf4j.Slf4j;

//This controller will be used to manage blogs
@Slf4j
@Controller
@RequestMapping("/blog")
public class BlogController {

	// Declare and autowire BlogBusinessInterface type variable
	@Autowired
	private BlogBusinessInterface blogService;

	@Autowired
	private HttpSession session;

	/**
	 * used to display CreateBlog view
	 * 
	 * @param Model - used to pass model to view
	 * @Return String (CreateBlog)
	 * 
	 **/
	@GetMapping("/")
	public String display(Model model) {
		// display login
		model.addAttribute("title", "Blog Form");
		model.addAttribute("blogModel", new BlogModel());
		return "CreateBlog";
	}

	/**
	 * used to display CreateBlog view
	 * 
	 * @param Model - used to pass model to view, BlogModel - used to get blog being
	 *              updated
	 * @Return String (UpdateBlog)
	 * 
	 **/
	@PostMapping("/displayEditPage")
	public String displayEditPage(@ModelAttribute BlogModel blogModel, Model model) {
		//log.info("Enter BlogController.displayEditPage()");

		// call to business service
		blogModel = blogService.findById(blogModel.getId());

		// display update blog
		model.addAttribute("title", "Edit Form");
		model.addAttribute("blogModel", blogModel);

		//log.info("Exit BlogController.displayEditPage()");
		return "UpdateBlog";
	}

	/**
	 * used to display remove blog page
	 * 
	 * @param Model - used to pass model to view, BlogModel - used to get blog being
	 *              deleted
	 * @Return String (ConfirmBlogDeletion)
	 * 
	 **/
	@PostMapping("/displayRemovePage")
	public String displayRemovePage(@ModelAttribute BlogModel blogModel, Model model) {
		//log.info("Enter BlogController.displayRemovePage()");

		// call to business service
		blogModel = blogService.findById(blogModel.getId());

		// display confirmation page
		model.addAttribute("title", "Blog Form");
		model.addAttribute("blogModel", blogModel);

		//log.info("Exit BlogController.displayRemovePage()");
		return "ConfirmBlogDeletion";
	}

	/**
	 * used to add blog
	 * 
	 * @param: Model - used to pass model to view, BlogModel - used to get blog info
	 *               from form that user filled out
	 * @return type: String (DisplayMyBlogs)
	 **/
	@PostMapping("/doPost")
	public String doPost(@Valid BlogModel blogModel, BindingResult bindingResult, Model model) {
		log.info("Enter BlogController.doPost()");

		// if there were binding errors, or user was not registered successfully
		if (bindingResult.hasErrors()) {

			// return register view
			model.addAttribute("title", "Blog Form");

			//log.info("Exit RegisterController.doRegister() with binding errors");
			return "CreateBlog";
		}

		// get user id from session
		int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());

		// set user id in blog model to user id from session
		blogModel.setUserId(userId);

		// call insert() method in BlogBusinessService and pass blog provided by
		// user
		// store value returned in boolean
		boolean isCreated = blogService.insert(blogModel);

		if (isCreated != true) {

			// return register view
			model.addAttribute("title", "Register Form");

			log.info("Exit RegisterController.doRegister() with unsuccessful registration");
			return "CreateBlog";
		}

		// store what findAll returns in blogs variable
		List<BlogModel> myBlogs = blogService.findAllById(userId);

		// pass model to view
		model.addAttribute("myBlogs", myBlogs);

		//log.info("Exit BlogController.doPost() with list of blogs");

		// return DisplayBlogs view
		return "DisplayMyBlogs";
	}

	/**
	 * used to view all blogs
	 * 
	 * @param: Model - used to pass model to view
	 * @return type: String (DisplayAllBlogs)
	 **/
	@GetMapping("/viewAll")
	public String viewAll(@Valid BlogModel blogModel, BindingResult bindingResult, Model model) {
		//log.info("Enter BlogController.viewAll()");

		// store what findAll returns in blogs variable
		List<BlogModel> allBlogs = blogService.findAll();

		// pass list to view
		model.addAttribute("allBlogs", allBlogs);

		//log.info("Exit BlogController.viewAll() with list of blogs");

		// return DisplayAllBlogs view
		return "DisplayAllBlogs";
	}

	/**
	 * This method is used to pass all of a users blogs to a view
	 * 
	 * @param blogModel     - used to get blog model
	 * @param bindingResult - used for field validation
	 * @param model         - used to pass model to view
	 * @param auth          - used to validate user login
	 * @return String (DisplayMyBlogs)
	 */
	@GetMapping("/viewMine")
	public String viewMine(@Valid BlogModel blogModel, BindingResult bindingResult, Model model) {
		//log.info("Enter BlogController.viewMine()");

		// store users id which is in the session
		int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());

		// store what findAll returns in blogs variable
		List<BlogModel> myBlogs = blogService.findAllById(userId);

		// pass list to view
		model.addAttribute("myBlogs", myBlogs);

		//log.info("Exit BlogController.viewMine() with list of users blogs");

		// return DisplayAllBlogs view
		return "DisplayMyBlogs";
	}

	/**
	 * This method is used to update a blog
	 * 
	 * @param newBlogModel  - used to store the update version of the blog
	 * @param oldBlogModel  - used to get a users id
	 * @param bindingResult - used for field validation
	 * @param model         - used to pass model to view
	 * @return String (DisplayMyBlogs)
	 */
	@PostMapping("/doEdit")
	public String doEdit(@Valid @ModelAttribute BlogModel newBlogModel, BindingResult bindingResult, Model model) {
		//log.info("Enter BlogController.doEdit()");

		// if there were binding errors, or blog was not deleted successfully
		if (bindingResult.hasErrors()) {
			// return CreateBlog view
			model.addAttribute("title", "Blog Form");

			//log.info("Exit BlogController.doEdit() with unsuccessful blog edit");

			return "UpdateBlog";
		}

		// update blog, store what is returned in boolean
		boolean isUpdated = blogService.edit(newBlogModel);

		if (isUpdated != true) {

			// return register view
			model.addAttribute("title", "Register Form");

			//log.info("Exit RegisterController.doRegister() with unsuccessful registration");
			return "UpdateBlog";
		}

		// store users id which is in the session
		int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());

		// store what findAll returns in blogs variable
		List<BlogModel> myBlogs = blogService.findAllById(userId);

		// pass list to view
		model.addAttribute("myBlogs", myBlogs);
		//log.info("Exit BlogController.doEdit() ");

		// return DisplayMyBlogs view
		return "DisplayMyBlogs";
	}

	/**
	 * This method is used to delete a blog
	 * 
	 * @param blogModel     - used to get the blog id
	 * @param bindingResult - used for field validation
	 * @param model         - used to pass model to view
	 * @return String (Display<yBlogs)
	 */
	@PostMapping("/doRemove")
	public String doRemove(@ModelAttribute BlogModel blogModel, BindingResult bindingResult, Model model) {
		//log.info("Enter BlogController.doRemove()");

		// get blog by id
		blogModel = blogService.findById(blogModel.getId());

		// remove blog, store what is returned in boolean
		boolean isDeleted = blogService.remove(blogModel);

		// if there were binding errors, or blog was not deleted successfully
		if (isDeleted == false) {
			// return CreateBlog view
			model.addAttribute("title", "Blog Form");

			//log.info("Exit BlogController.doRemove() with unsuccessful blog deletion");

			return "ConfirmBlogDeletion";
		}

		// one blog was deleted from users list of blogs, store new list in myBlogs
		List<BlogModel> myBlogs = blogService.findAllById(blogModel.getUserId());

		// pass list to view
		model.addAttribute("myBlogs", myBlogs);

		//log.info("Exit BlogController.doRemove() ");
		// return DisplayMyBlogs view
		return "DisplayMyBlogs";
	}
}