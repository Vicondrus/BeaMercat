package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entities.User;
import com.project.services.interfaces.UserDaoI;

@Controller  
public class MainController {
	
	@Autowired 
	private UserDaoI userDao;
	
	@GetMapping("/add")
	public String getAddNewUser() {
		return "addUser";
	}

	@PostMapping("/addAux") // Map ONLY POST Requests
	public @ResponseBody String postAddNewUser (User user, BindingResult result) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		if(result.hasErrors()) {
			System.out.println("LOL");
			return "CE plm";
		}

		userDao.insert(user);
		return "Saved";
	}

	@GetMapping("/all")
	public String getAllUsers(ModelMap map) {
		// This returns a JSON or XML with the users
		List<User> l = userDao.findAll();
		map.addAttribute("list", l);
		return "main";
	}
}