package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entities.Address;
import com.project.entities.Product;
import com.project.entities.ShoppingCart;
import com.project.entities.User;
import com.project.entities.UserType;
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
		Address ad = new Address("Romania","Cluj-Napoca","Aleea Bucura", 1, 99, (long)4000321);
		Product p1 = new Product();
		p1.setName("CAL");
		Product p2 = new Product();
		p2.setName("VITEL");
		ShoppingCart sc = new ShoppingCart();
		User u = new User( null,"Victor", "victor.padurean@yahoo.com", ad, sc, UserType.CUSTOMER);
		User a = new User(null,"admin","admin@gmail.com");
		u.addProductToCart(p1, 2);
		u.addProductToCart(p2, 3);
		userDao.insert(u);
		userDao.insert(a);
		List<User> l = userDao.findAll();
		map.addAttribute("list", l);
		return "main";
	}
}