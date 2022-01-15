package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//This controller will only be used to display the landing page
@Controller
@RequestMapping("/")
public class IndexController {
	
	/**
	 * This method is used to display the index view
	 * @param model - used to pass model to view
	 * @return String (index)
	 */
	@GetMapping("/")
    public String display(Model model) {
        //display index
        return "index";
    }
}
