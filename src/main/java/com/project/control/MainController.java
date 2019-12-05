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
import com.project.entities.ShoppingCart;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.services.interfaces.CategoryDaoI;
import com.project.services.interfaces.OrderDaoI;
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

	@Autowired
	private OrderDaoI orderDao;

	@GetMapping("/logoutAndEmpty")
	public String getLogout(Principal principal) {
		User u = userDao.getByUsername(new User(principal.getName()));
		userDao.discardCartAndRestock(u);
		return "redirect:/logout";
	}

	@GetMapping("/admin/addCategory")
	public String getAddNewCategory() {
		return "addCategory";
	}

	@PostMapping("/admin/addCategoryAux")
	public String postAddNewCategory(Category category, BindingResult result) {
		category.setCategoryStatus(Status.ACTIVE);
		categoryDao.saveCategory(category);
		return "Saved";
	}

	@GetMapping("/admin/addProvider")
	public String getAddNewProvider() {
		return "addProvider";
	}

	@PostMapping("/admin/addProviderAux")
	public String postAddNewProvider(Provider provider, Address address, BindingResult result) {
		provider.setProviderStatus(Status.ACTIVE);
		provider.setAddress(address);
		providerDao.saveProvider(provider);
		return "Saved";
	}

	@GetMapping("/admin/addProduct")
	public String getAddNewProduct(ModelMap map) {
		map.addAttribute("categories",
				categoryDao.getAllActive().stream().map(Category::getName).collect(Collectors.toList()));
		map.addAttribute("providers",
				providerDao.getAllActive().stream().map(Provider::getName).collect(Collectors.toList()));
		return "addProduct";
	}

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

	@GetMapping("/admin/addUser")
	public String getAdminAddNewUser() {
		return "redirect:/addUser";
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

	@GetMapping("/admin/listAllUsers")
	public String getListAllUsers(ModelMap map) {
		map.addAttribute("list", userDao.getAll());
		return "showAllUsers";
	}

	@GetMapping("/user/viewProduct")
	public String getViewProduct(ModelMap map, String id) {
		Product aux = new Product();
		aux.setId(id);
		Product p = productDao.getById(aux);
		if (p == null)
			map.addAttribute("error", "No such product");
		map.addAttribute("product", p);
		map.addAttribute("image", Base64.getEncoder().encodeToString(p.getImage().getData()));
		return "showProduct";
	}

	@GetMapping("/user/browseProducts")
	public String getBrowseProducts() {
		return "redirect:/listAllProducts";
	}

	@GetMapping("/admin/listProducts")
	public String getAdminBrowseProducts() {
		return "redirect:/listAllProducts";
	}

	@GetMapping("/user/checkout")
	public String getCheckout(ModelMap map, Principal principal) {
		ShoppingCart sc = userDao.getByUsername(new User(principal.getName())).getShoppingCart();
		List<ProductQuantity> p = sc.getProducts();
		map.addAttribute("total", sc.getTotalPrice());
		map.addAttribute("list", p);
		return "checkout";
	}

	@PostMapping("/user/checkoutAux")
	public String postCheckout(Address address, Principal principal) {
		userDao.placeOrder(new User(principal.getName()), address);
		return "redirect:/user/listOrders";
	}

	@GetMapping("/user/listOrders")
	public String getUserListOrders(ModelMap map, Principal principal) {
		map.addAttribute("list", orderDao.getByCustomer(new User(principal.getName())));
		return "listOrders";
	}

	@GetMapping("/user/viewOrder")
	public String getUserViewOrder(String id, ModelMap map) {
		map.addAttribute("order", orderDao.getById(new Order(id)));
		return "inspectOrder";
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

	@GetMapping("/user/updateUser")
	public String getUserUpdateUser(ModelMap map, Principal principal) {
		map.addAttribute("user", userDao.getByUsername(new User(principal.getName())));
		return "updateUser";
	}

	@PostMapping("/user/updateUserAux")
	public String postUserUpdateUser(User user, Address address) {
		return postUpdateUserCommon(user, address);
	}

	@PostMapping("/updateUserAux")
	public String postUpdateUserCommon(User user, Address address) {
		user.setAddress(address);
		userDao.updateUser(user);
		return "redirect:/user/viewUser";
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
		userDao.addToCart(u, p, quantity);
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
		userDao.updateQuantityCart(u, new Product(name), quant);
		return "redirect:/user/viewShoppingCart";
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
}