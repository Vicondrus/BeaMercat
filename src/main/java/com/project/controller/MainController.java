package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entities.Address;
import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.services.interfaces.CategoryDaoI;
import com.project.services.interfaces.ProductDaoI;
import com.project.services.interfaces.UserDaoI;

@Controller
public class MainController {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private CategoryDaoI categoryDao;

	@Autowired
	private ProductDaoI productDao;

	@GetMapping("/addCategory")
	public String getAddNewCategory() {
		return "addCategory";
	}

	@PostMapping("/addCategoryAux")
	public String postAddNewCategory(Category category, BindingResult result) {
		category.setCategoryStatus(Status.ACTIVE);
		categoryDao.saveCategory(category);
		return "Saved";
	}

	@GetMapping("/addProduct")
	public String getAddNewProduct(ModelMap map) {
		map.addAttribute("categories", categoryDao.getAll());
		return "addProduct";
	}

	@PostMapping("/addProductAux")
	public String postAddNewProduct(Product product, BindingResult result,
			@RequestParam("category") String categoryName) {
		if (result.hasFieldErrors())
			System.out.println("Something went wrong creating the product");
		product.setCategory(categoryDao.findByName(new Category(null, categoryName.trim())));
		// product.setProductStatus(Status.ACTIVE);
		productDao.saveProduct(product);
		return "Saved";
	}

	@GetMapping("/addUser")
	public String getAddNewUser() {
		return "addUser";
	}

	@PostMapping("/addUserAux")
	public String postAddNewUser(User user, Address address, BindingResult result) {
		if (result.hasFieldErrors())
			System.out.println("Something went wrong creating the user");
		user.setAddress(address);
		userDao.saveUser(user);
		return "Saved";
	}

	@GetMapping("/listAllUsers")
	public String getListAllUsers(ModelMap map) {
		map.addAttribute("list", userDao.getAll());
		return "showAllUsers";
	}

	@GetMapping("/listAllProducts")
	public String getListAllProducts(ModelMap map) {
		map.addAttribute("list", productDao.getAll());
		return "showAllProducts";
	}

	@PostMapping("/mockPost")
	public void postMock(@RequestParam("quant") Integer quantity) {
		Integer q = quantity;
	}

	@GetMapping("/all")
	public String getAllUsers(ModelMap map) {
		// This returns a JSON or XML with the users
		// Address ad = new Address("Romania", "Cluj-Napoca", "Aleea Bucura", 1, 99,
		// "4000321");
		// Product p1 = new Product();
		// p1.setName("CAL");
		// Product p2 = new Product();
		// p2.setName("VITEL");
		// ShoppingCart sc = new ShoppingCart();
		// Product p3 = new Product();
		// p3.setName("CAL");
		// sc.addProductToCart(p1, 2);
		// sc.addProductToCart(p2, 3);
		// sc.addProductToCart(p1, 100);
		// User u = new User(null, "Victor", "lol", "victor.padurean@yahoo.com", ad, sc,
		// UserType.CUSTOMER);
		// User a = new User(null, "admin","hr","he", "lol", "admin@gmail.com", "1234");
		// u.addProductToCart(p1, 2);
		// u.addProductToCart(p2, 3);
		// userDao.saveUser(u);
		// userDao.saveUser(a);
		List<User> l = userDao.getAll();
		map.addAttribute("list", l);
		return "main";
	}
}