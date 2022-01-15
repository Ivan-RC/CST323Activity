package com.gcu.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.SecurityBusinessService;
import com.gcu.business.SecurityBusinessInterface;
import com.gcu.model.LoginModel;

//import lombok.extern.slf4j.Slf4j;

//This controller will be used for security purposes
@Controller
@RequestMapping("/login")
public class SecurityController {	
	//Declare and autowire SecurityBusinessServiceInterface type variable
	@Autowired
	SecurityBusinessService securityService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * This method is used to display the login view
	 * @param model - used to pass model to view
	 * @return String (login)
	 */
	@GetMapping("/")
    public String display(Model model) {
        //display login
        model.addAttribute("title", "Login Form");
        model.addAttribute("loginModel", new LoginModel());
        return "login";
    }
	
	/**
	 * This method is used to authenticate users trying to login
	 * @param loginModel - used to get the users credentials 
	 * @param bindingResult - used for field validation
	 * @param model - used to pass the model to the view
	 * @param session - used to stores the user id when they login successfully 
	 * @return String (home)
	 */
    @PostMapping("/doLogin")
    public String doLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model, HttpSession session) {
    	//log.info("Enter SecurityController.doLogin()");
    	
    	//call authenticate() method in SecurityBusinessService and pass credentials provided by user
    	//store value returned in boolean
    	boolean isAuthenticated = securityService.authenticate(loginModel);
    	
    	//if there were binding errors, or user was not authenticated successfully
        if(bindingResult.hasErrors() || isAuthenticated == false) {
        	//return login view
            model.addAttribute("title", "Login Form");
            
            //log.info("Exit SecurityController.doLogin() with binding errors, or unsuccessful login");
            return "login";
        }
        
        //add loginModel to view
        model.addAttribute("loginModel", loginModel);
        
        //store users id in session
        session.setAttribute("USER_ID", loginModel.getUserId());
        
        //log.info("Exit SecurityController.doLogin() with successful login");
        //return home view
        return "home";
    }
}