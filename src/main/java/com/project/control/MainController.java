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
import com.project.entities.Order;
import com.project.entities.Product;
import com.project.entities.ProductQuantity;
import com.project.entities.Provider;
import com.project.entities.Review;
import com.project.entities.ShoppingCart;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.exceptions.InvalidArgumentsException;
import com.project.services.interfaces.CategoryServiceI;
import com.project.services.interfaces.OrderServiceI;
import com.project.services.interfaces.ProductServiceI;
import com.project.services.interfaces.ProviderServiceI;
import com.project.services.interfaces.ReviewServiceI;
import com.project.services.interfaces.UserServiceI;

//the controller from the MVC architecture pattern
@Controller
public class MainController {

	//spring will instantiate the autowired fields at runtime
	
	@Autowired
	private UserServiceI userDao;

	@Autowired
	private CategoryServiceI categoryDao;

	@Autowired
	private ProductServiceI productDao;

	@Autowired
	private ProviderServiceI providerDao;

	@Autowired
	private OrderServiceI orderDao;

	@Autowired
	private ReviewServiceI reviewDao;
	
	//methods that handle GET and POST requests

	//the method that discards the items in the cart before logging out
	
	@GetMapping("/logoutAndEmpty")
	public String getLogout(Principal principal) {
		User u = userDao.getByUsername(new User(principal.getName()));
		userDao.discardCartAndRestock(u);
		return "redirect:/logout";
	}
	
	//the method that returns the page necessary for category creation

	@GetMapping("/admin/addCategory")
	public String getAddNewCategory() {
		return "addCategory";
	}
	
	//the method that handles creation of a new category

	@PostMapping("/admin/addCategoryAux")
	public String postAddNewCategory(Category category, BindingResult result) {
		category.setCategoryStatus(Status.ACTIVE);
		categoryDao.saveCategory(category);
		return "redirect:/admin/listCategories";
	}
	
	//the method that returns the page necessary for provider creation

	@GetMapping("/admin/addProvider")
	public String getAddNewProvider() {
		return "addProvider";
	}
	
	//the method that handles creation of a new provider

	@PostMapping("/admin/addProviderAux")
	public String postAddNewProvider(Provider provider, Address address, BindingResult result) {
		provider.setProviderStatus(Status.ACTIVE);
		provider.setAddress(address);
		providerDao.saveProvider(provider);
		return "redirect:/admin/listProviders";
	}
	
	//the method that returns the page necessary for product creation

	@GetMapping("/admin/addProduct")
	public String getAddNewProduct(ModelMap map) {
		map.addAttribute("categories",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		map.addAttribute("providers",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		return "addProduct";
	}
	
	//the method that handles creation of a new product

	@PostMapping("/admin/addProductAux")
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
		return "redirect:/admin/listProducts";
	}
	
	//the method that returns the page necessary for user creation
	//for the admin type of user

	@GetMapping("/admin/addUser")
	public String getAdminAddNewUser() {
		return "redirect:/addUser";
	}

	//the method that returns the page necessary for user registration
	
	@GetMapping("/addUser")
	public String getAddNewUser() {
		return "addUser";
	}
	
	//the method that handles creation of a new user

	@PostMapping("/addUserAux")
	public String postAddNewUser(User user, Address address, BindingResult result) {
		if (result.hasFieldErrors())
			System.out.println("Something went wrong creating the user");
		user.setAddress(address);
		userDao.saveUser(user);
		return "home";
	}
	
	//method handling retrieval of all users
	//returns a page that lists all users

	@GetMapping("/admin/listAllUsers")
	public String getListAllUsers(ModelMap map) {
		map.addAttribute("list", userDao.getAll());
		return "showAllUsers";
	}
	
	//method retrieving a product
	//returns a page with the product presentation

	@GetMapping("/user/viewProduct")
	public String getViewProduct(ModelMap map, String id, Principal principal) {
		Product aux = new Product();
		aux.setId(id);
		Product p = productDao.getById(aux);
		if (p == null)
			map.addAttribute("error", "No such product");
		map.addAttribute("product", p);
		map.addAttribute("image", Base64.getEncoder().encodeToString(p.getImage().getData()));
		List<Review> reviews = reviewDao.getAllActiveForProduct(aux);
		map.addAttribute("reviews", reviews);
		map.addAttribute("custReview",
				reviewDao.findCustomerReview(new User(principal.getName()), reviews));
		return "showProduct";
	}
	
	//method responsible for adding a review to a product
	
	@PostMapping("/user/addReview")
	public String postAddReview(Review review) {
		reviewDao.saveReview(review);
		return "redirect:/user/viewProduct?id="+review.getProductId();
	}
	
	//method responsible for modifying a review of a product
	
	@PostMapping("/user/modifyReview")
	public String postModifyReview(Review review) {
		reviewDao.updateReview(review);
		return "redirect:/user/viewProduct?id="+review.getProductId();
	}
	
	//method responsible for deleting a review from a product
	
	@PostMapping("/user/deleteReview")
	public String postDeleteReview(Review review) {
		reviewDao.deleteReview(review);
		return "redirect:/user/viewProduct?id="+review.getProductId();
	}

	@GetMapping("/user/browseProducts")
	public String getBrowseProducts() {
		return "redirect:/listAllProducts";
	}

	@GetMapping("/admin/listProducts")
	public String getAdminBrowseProducts() {
		return "redirect:/listAllProducts";
	}

	//method that returns the checkout page with the order's details
	
	@GetMapping("/user/checkout")
	public String getCheckout(ModelMap map, Principal principal) {
		ShoppingCart sc = userDao.getByUsername(new User(principal.getName())).getShoppingCart();
		List<ProductQuantity> p = sc.getProducts();
		map.addAttribute("total", sc.getTotalPrice());
		map.addAttribute("list", p);
		return "checkout";
	}
	
	//method responsible for placing an order

	@PostMapping("/user/checkoutAux")
	public String postCheckout(Address address, Principal principal) {
		userDao.placeOrder(new User(principal.getName()), address);
		return "redirect:/user/listOrders";
	}
	
	//method that returns the order completion page

	@GetMapping("/courier/completeOrder")
	public String getCompleteOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "completeOrder";
	}
	
	//method responsible for changing the order's status to completed

	@PostMapping("/completeOrderAux")
	public String postCompleteOrder(String id) {
		orderDao.completeOrder(new Order(id));
		return "redirect:/courier/viewOrders";
	}
	
	//method that returns a page with all an user's orders (logged in)

	@GetMapping("/user/listOrders")
	public String getUserListOrders(ModelMap map, Principal principal) {
		map.addAttribute("list", orderDao.getByCustomer(new User(principal.getName())));
		return "listOrders";
	}

	//method that returns a page with all the orders
	
	@GetMapping("/admin/listOrders")
	public String getAdminListOrders(ModelMap map, Principal principal) {
		map.addAttribute("list", orderDao.getAll());
		return "listOrders";
	}
	
	//method responsible for retrieving orders for a given user

	@PostMapping("/listOrdersAux")
	public String postAdminListOrders(String username, ModelMap map) {
		map.addAttribute("list", orderDao.getByCustomer(new User(username)));
		return "listOrders";
	}
	
	//method that returns a page with an order's details
	//for an user

	@GetMapping("/user/viewOrder")
	public String getUserViewOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "inspectOrder";
	}
	
	//order page for an admin

	@GetMapping("/admin/viewOrder")
	public String getAdminViewOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "inspectOrder";
	}
	
	//user method responsible for retrieving a page used for canceling orders

	@GetMapping("/user/cancelOrder")
	public String getUserCancelOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "cancelOrder";
	}
	
	//admin method responsible for adding a review to a product

	@GetMapping("/admin/cancelOrder")
	public String getAdminCancelOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "cancelOrder";
	}
	
	//method responsible for canceling an order

	@PostMapping("/cancelOrderAux")
	public String postCancelOrder(String id, Principal principal) {
		orderDao.cancelOrder(new Order(id));
		User user = userDao.getByUsername(new User(principal.getName()));
		if (user.getUserType().equals(UserType.ADMIN))
			return "redirect:/admin/listOrders";
		return "redirect:/user/listOrders";
	}

	@GetMapping("/user/searchProduct")
	public String getSearchProduct() {
		return "searchProduct";
	}

	@PostMapping("/user/searchProductAux")
	public String postSearchProduct(String name, ModelMap map) {
		List<Product> p = productDao.getAllActiveByNameLike(new Product(name));
		map.addAttribute("list", p);
		map.addAttribute("images", p.stream().map(x -> {
			if (x.getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getImage().getData());
		}).collect(Collectors.toList()));
		return "searchProduct";
	}

	@GetMapping("/user/searchByCategory")
	public String getSearchByCategory(ModelMap map) {
		map.addAttribute("cats",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		return "searchByCategory";
	}

	@PostMapping("/user/searchByCategoryAux")
	public String postSearchByCategory(ModelMap map, String cat) {
		map.addAttribute("cats",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		List<Product> p = productDao.getAllActiveByCatergory(new Category(cat));
		map.addAttribute("list", p);
		map.addAttribute("images", p.stream().map(x -> {
			if (x.getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getImage().getData());
		}).collect(Collectors.toList()));
		return "searchByCategory";
	}

	@GetMapping("/user/searchByProvider")
	public String getSearchByProvider(ModelMap map) {
		map.addAttribute("provs",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		return "searchByProvider";
	}

	@PostMapping("/user/searchByProviderAux")
	public String postSearchByProvider(ModelMap map, String prov) {
		map.addAttribute("provs",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		List<Product> p = productDao.getAllActiveByProvider(new Provider(prov));
		map.addAttribute("list", p);
		map.addAttribute("images", p.stream().map(x -> {
			if (x.getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getImage().getData());
		}).collect(Collectors.toList()));
		return "searchByProvider";
	}

	@GetMapping("/user/main")
	public String getUserMain() {
		return "redirect: /main";
	}

	@GetMapping("/listAllProducts")
	public String getListAllProducts(ModelMap map, Principal principal) {
		List<Product> p;
		if (userDao.getByUsername(new User(principal.getName())).getUserType().equals(UserType.ADMIN))
			p = productDao.getAll();
		else
			p = productDao.getAllActive();
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

	@GetMapping("/courier/viewUser")
	public String getCourierViewUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "customerViewUser";
	}

	@GetMapping("/user/updateUser")
	public String getUserUpdateUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "updateUser";
	}

	@GetMapping("/courier/updateUser")
	public String getCourierUpdateUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "updateUser";
	}

	@PostMapping("/updateUserAux")
	public String postUpdateUserCommon(User user, Address address) {
		user.setAddress(address);
		userDao.updateUser(user);
		return "redirect:/main";
	}

	@GetMapping("/user/viewShoppingCart")
	public String getUserShoppingCart(ModelMap map, Principal principal) {
		ShoppingCart sc = userDao.getByUsername(new User(principal.getName())).getShoppingCart();
		List<ProductQuantity> p = sc.getProducts();
		map.addAttribute("total", sc.getTotalPrice());
		map.addAttribute("list", p);
		map.addAttribute("images", p.stream().map((ProductQuantity x) -> {
			if (x.getProduct().getImage() == null)
				return null;
			return Base64.getEncoder().encodeToString(x.getProduct().getImage().getData());
		}).collect(Collectors.toList()));
		return "inspectShoppingCart";
	}

	@PostMapping("/user/addToShoppingCart")
	public String postAddShoppingCart(String productId, Integer quantity, Principal principal) {
		Product p = new Product();
		p.setId(productId);
		p = productDao.getById(p);
		User u = new User(principal.getName());
		try {
			userDao.addToCart(u, p, quantity);
		} catch (InvalidArgumentsException e) {
			return "redirect:/user/viewProduct?id=" + p.getId() + "&error=" + e.getMessage();
		}
		return "redirect:/user/viewShoppingCart";
	}

	@PostMapping("/user/removeFromShoppingCart")
	public String getRemoveFromShoppingCart(String name, Principal principal) {
		Product p = new Product(name);
		User u = new User(principal.getName());
		userDao.removeFromCart(u, p);
		return "redirect:/user/viewShoppingCart";
	}

	@PostMapping("/user/updateShoppingCart")
	public String postUpdateShoppingCart(Integer quant, String name, Principal principal) {
		User u = new User(principal.getName());
		try {
			userDao.updateQuantityCart(u, new Product(name), quant);
		} catch (InvalidArgumentsException e) {
			return "redirect:/user/viewShoppingCart?error=" + e.getMessage();
		}
		return "redirect:/user/viewShoppingCart";
	}

	@GetMapping("/")
	public String getRoot() {
		return "redirect:/home";
	}

	@GetMapping("/main")
	public String getAllUsers(ModelMap map, Principal principal) {
		if (userDao.getByUsername(new User(principal.getName())).getUserType().equals(UserType.COURIER))
			return "redirect:/courier/viewOrders";
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

	@GetMapping("/courier/viewOrders")
	public String getCourierViewOrders(ModelMap map, Principal principal) {
		map.addAttribute("list", userDao.getByUsername(new User(principal.getName())).getCourierOrders());
		return "listOrders";
	}

	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	@GetMapping("/admin/viewUser")
	public String adminViewUser(String username, ModelMap map) {
		map.addAttribute("user", userDao.getByUsername(new User(username)));
		return "customerViewUser";
	}

	@GetMapping("/admin/updateUser")
	public String getAdminUpdateUser(ModelMap map, String username) {
		map.addAttribute("user", userDao.getByUsername(new User(username)));
		return "updateUser";
	}

	@GetMapping("/admin/deleteUser")
	public String getDeleteUser(ModelMap map, String username) {
		map.addAttribute("user", userDao.getByUsername(new User(username)));
		return "deleteUser";
	}

	@PostMapping("deleteUserAux")
	public String postDeleteUser(User user) {
		userDao.deleteUser(user);
		return "redirect:/admin/listAllUsers";
	}

	@GetMapping("/admin/viewProduct")
	public String getViewProductAdmin(ModelMap map, String id) {
		Product aux = new Product();
		aux.setId(id);
		Product p = productDao.getById(aux);
		if (p == null)
			map.addAttribute("error", "No such product");
		map.addAttribute("product", p);
		map.addAttribute("image", Base64.getEncoder().encodeToString(p.getImage().getData()));
		return "showProduct";
	}

	@GetMapping("/admin/updateProduct")
	public String getUpdateProduct(ModelMap map, String id) {
		Product aux = new Product();
		aux.setId(id);
		Product p = productDao.getById(aux);
		if (p == null)
			map.addAttribute("error", "No such product");
		map.addAttribute("product", p);
		map.addAttribute("categories",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		map.addAttribute("providers",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		map.addAttribute("image", Base64.getEncoder().encodeToString(p.getImage().getData()));
		return "updateProduct";
	}

	@PostMapping("/admin/updateProductAux")
	public String postUpdateProduct(ModelMap map, Product product, String category, String provider,
			@RequestParam("img") MultipartFile image) {
		product.setProvider(providerDao.findByName(new Provider(provider.trim())));
		product.setCategory(categoryDao.findByName(new Category(null, category.trim())));
		try {
			productDao.updateProductImage(product, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/listProducts";
	}

	@GetMapping("/admin/deleteProduct")
	public String getDeleteProduct(ModelMap map, String id) {
		Product aux = new Product();
		aux.setId(id);
		Product p = productDao.getById(aux);
		if (p == null)
			map.addAttribute("error", "No such product");
		map.addAttribute("product", p);
		map.addAttribute("image", Base64.getEncoder().encodeToString(p.getImage().getData()));
		return "deleteProduct";
	}

	@PostMapping("/admin/deleteProductAux")
	public String postDeleteProduct(Product product) {
		productDao.deleteProduct(product);
		return "redirect:/admin/listProducts";
	}

	@GetMapping("/admin/listProviders")
	public String getListProviders(ModelMap map) {
		map.addAttribute("list", providerDao.getAll());
		return "showAllProviders";
	}

	@GetMapping("/admin/viewProvider")
	public String getViewProvider(ModelMap map, String name) {
		map.addAttribute("provider", providerDao.findByName(new Provider(name)));
		return "viewProvider";
	}

	@GetMapping("/admin/updateProvider")
	public String getUpdateProvider(ModelMap map, String name) {
		map.addAttribute("provider", providerDao.findByName(new Provider(name)));
		return "updateProvider";
	}

	@PostMapping("/updateProviderAux")
	public String postUpdateProvider(ModelMap map, Provider provider, Address address) {
		provider.setAddress(address);
		providerDao.updateProvider(provider);
		return "redirect:/admin/listProviders";
	}

	@GetMapping("/admin/deleteProvider")
	public String getDeleteProvider(ModelMap map, String name) {
		map.addAttribute("provider", providerDao.findByName(new Provider(name)));
		return "deleteProvider";
	}

	@PostMapping("/deleteProviderAux")
	public String postDeleteProvider(ModelMap map, Provider provider) {
		providerDao.deleteProvider(provider);
		return "redirect:/admin/listProviders";
	}

	@GetMapping("/admin/listCategories")
	public String getListCategories(ModelMap map) {
		map.addAttribute("list", categoryDao.getAll());
		return "showAllCategories";
	}

	@GetMapping("/admin/viewCategory")
	public String getViewCategory(ModelMap map, String name) {
		map.addAttribute("category", categoryDao.findByName(new Category(name)));
		return "viewCategory";
	}

	@GetMapping("/admin/updateCategory")
	public String getUpdateCategory(ModelMap map, String name) {
		map.addAttribute("category", categoryDao.findByName(new Category(name)));
		return "updateCategory";
	}

	@PostMapping("/updateCategoryAux")
	public String postUpdateCategory(ModelMap map, Category category) {
		categoryDao.updateCategory(category);
		return "redirect:/admin/listCategories";
	}

	@GetMapping("/admin/deleteCategory")
	public String getDeleteCategory(ModelMap map, String name) {
		map.addAttribute("category", categoryDao.findByName(new Category(name)));
		return "deleteCategory";
	}

	@PostMapping("/admin/deleteCategoryAux")
	public String postDeleteCategory(ModelMap map, Category category) {
		categoryDao.deleteCategory(category);
		return "redirect:/admin/listCategories";
	}

}