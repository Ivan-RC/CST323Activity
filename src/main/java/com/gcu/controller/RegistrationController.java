package com.gcu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.RegistrationBusinessInterface;
import com.gcu.model.RegisterModel;

//import lombok.extern.slf4j.Slf4j;

//This controller will be used for registration
@Controller
@RequestMapping("/register")
public class RegistrationController {

	// Declare and autowire RegistrationBusinessInterface type variable
	@Autowired
	private RegistrationBusinessInterface registrationService;

	/**
	 * This method is used to display the registration page
	 * 
	 * @param model - used to pass model to view
	 * @return String (register)
	 */
	@GetMapping("/")
	public String display(Model model) {
		// display register
		model.addAttribute("title", "Registration Form");
		model.addAttribute("registerModel", new RegisterModel());
		return "register";
	}

	/**
	 * This method is used to register a user in the database
	 * 
	 * @param registerModel - used to get credentials and user info from form that
	 *                      user filled out
	 * @param bindingResult - used for field validation
	 * @param model         - used to pass model to view
	 * @return String (login)
	 */
	@PostMapping("/doRegister")
	public String doRegister(@Valid RegisterModel registerModel, BindingResult bindingResult, Model model) {
		//log.info("Enter RegisterController.doRegister()");

		// if there were binding errors, or user was not registered successfully
		if (bindingResult.hasErrors()) {

			// return register view
			model.addAttribute("title", "Register Form");

			//log.info("Exit RegisterController.doRegister() with binding errors");
			return "register";
		}

		boolean isCreated = registrationService.insert(registerModel);

		if (isCreated != true) {

			// return register view
			model.addAttribute("title", "Register Form");

			//log.info("Exit RegisterController.doRegister() with unsuccessful registration");
			return "register";
		}

		model.addAttribute("loginModel", registerModel.getCredentials());

		//log.info("Exit RegisterController.doRegister() with successful registration");

		// return login view
		return "login";
	}
}
