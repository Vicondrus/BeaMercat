package com.project.control;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Address;
import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.services.interfaces.CategoryDaoI;
import com.project.services.interfaces.ProductDaoI;
import com.project.services.interfaces.ProviderDaoI;
import com.project.services.interfaces.UserDaoI;

@Controller
public class MainController {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private CategoryDaoI categoryDao;

	@Autowired
	private ProductDaoI productDao;

	@Autowired
	private ProviderDaoI providerDao;

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

	@GetMapping("/addProvider")
	public String getAddNewProvider() {
		return "addProvider";
	}

	@PostMapping("/addProviderAux")
	public String postAddNewProvider(Provider provider, Address address, BindingResult result) {
		provider.setProviderStatus(Status.ACTIVE);
		provider.setAddress(address);
		providerDao.saveProvider(provider);
		return "Saved";
	}

	@GetMapping("/addProduct")
	public String getAddNewProduct(ModelMap map) {
		map.addAttribute("categories",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		map.addAttribute("providers",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		return "addProduct";
	}

	@PostMapping("/addProductAux")
	public String postAddNewProduct(Product product, BindingResult result, String category, String provider,
			@RequestParam("image") MultipartFile image) {
		if (result.hasFieldErrors())
			System.out.println("Something went wrong creating the product");
		product.setProvider(providerDao.findByName(new Provider(provider.trim())));
		product.setCategory(categoryDao.findByName(new Category(null, category.trim())));
		// product.setProductStatus(Status.ACTIVE);
		if (image == null || image.isEmpty())
			productDao.saveProduct(product);
		else
			try {
				productDao.saveProductWithImage(product, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		return "home";
	}

	@GetMapping("/listAllUsers")
	public String getListAllUsers(ModelMap map) {
		map.addAttribute("list", userDao.getAll());
		return "showAllUsers";
	}

	@GetMapping("/listAllProducts")
	public String getListAllProducts(ModelMap map) {
		List<Product> p = productDao.getAll();
		map.addAttribute("list", p);
		map.addAttribute("images", p.stream().map(x -> {
			if (x.getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getImage().getData());
		}).collect(Collectors.toList()));
		return "showAllProducts";
	}

	@GetMapping("/user/viewUser")
	public String getUserViewUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "customerViewUser";
	}
	
	@GetMapping("/user/updateUser")
	public String getUserUpdateUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "updateUser";
	}

	@GetMapping("user/shoppingCart")
	public String getUserShoppingCart(ModelMap map, Principal principal) {
		map.addAttribute("shoppingCart", userDao.getByUsername(new User(principal.getName())).getShoppingCart());
		return "inspectShoppingCart";
	}

	@GetMapping("/")
	public String getRoot() {
		return "redirect:/home";
	}

	@GetMapping("/main")
	public String getAllUsers(ModelMap map) {
		List<Product> l = productDao.getAllActive();
		Collections.shuffle(l);
		l = l.stream().limit(5).collect(Collectors.toList());
		map.addAttribute("images", l.stream().map(x -> {
			if (x.getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getImage().getData());
		}).collect(Collectors.toList()));
		map.addAttribute("list", l);
		return "main";
	}

	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
}