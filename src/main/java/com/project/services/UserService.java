package com.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.Product;
import com.project.entities.ProductQuantity;
import com.project.entities.ShoppingCart;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.exceptions.InvalidArgumentsException;
import com.project.repos.UserRepository;
import com.project.services.distributionstrategies.DistributionStrategy;
import com.project.services.interfaces.OrderServiceI;
import com.project.services.interfaces.ProductServiceI;
import com.project.services.interfaces.UserServiceI;

//service class that wraps the data access for user
//and provides application logic

@Service
public class UserService implements UserServiceI {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductServiceI productDao;

	@Autowired
	private OrderServiceI orderDao;
	
	//methods for adding, deleting, updating and retrieving products from the database
	//with additional, corresponding application logic

	private boolean isValidPhone(String s) {
		Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

		Matcher m = p.matcher(s);
		return (m.find() && m.group().equals(s));
	}

	@Override
	public List<User> getByRole(UserType role) {
		return userRepo.findByUserType(role);
	}

	
	//logic needed to save the user with their corresponding fields
	//according to their type
	//the fields that are not used for a certain user are left null
	//they do not appear in the database
	//the usernames are unique
	@Transactional
	@Override
	public User saveUser(User user) throws InvalidArgumentsException {
		if (user == null)
			return null;
		if (!isValidPhone(user.getTelephone()))
			throw new InvalidArgumentsException("Telephone number not valid");
		if(user.getAddress().getNumber()<0)
			throw new InvalidArgumentsException("Address number not valid");
		user.setUsername(user.getUsername().trim());
		if (userRepo.findByUsername(user.getUsername()) != null)
			throw new InvalidArgumentsException("Username already exists");
		if (user.getUserType() == null)
			user.setUserType(UserType.CUSTOMER);
		if (user.getUserType().equals(UserType.CUSTOMER) && user.getShoppingCart() == null)
			user.setShoppingCart(new ShoppingCart());
		user.setUserStatus(Status.ACTIVE);
		if (user.getUserType().equals(UserType.COURIER) && user.getCourierOrders() == null) {
			user.setCourierOrders(new ArrayList<Order>());
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
		return userRepo.save(user);
	}

	public List<User> getAll() {
		return userRepo.findAll();
	}

	
	//updating the user
	//logic needed to convert a user from one type to another
	@Transactional
	@Override
	public User updateUser(User user) {
		if (user == null)
			return null;
		User found = getByUsername(user);
		if (found == null) {
			return null;
		} else {
			if (user.getPassword() == null || user.getPassword().equals("")) {
				user.setPassword(found.getPassword());
			}
			user.setId(found.getId());
			if (user.getUserType() == null) {
				user.setUserType(found.getUserType());
				user.setShoppingCart(found.getShoppingCart());
				user.setCourierOrders(found.getCourierOrders());
			}
			if (user.getUserType().equals(UserType.CUSTOMER)) {
				if (user.getShoppingCart() == null)
					user.setShoppingCart(new ShoppingCart());
				user.setCourierOrders(null);
			}
			if (user.getUserType().equals(UserType.ADMIN)) {
				user.setShoppingCart(null);
				user.setCourierOrders(null);
			}
			if (user.getUserType().equals(UserType.COURIER)) {
				user.setShoppingCart(null);
				if (user.getCourierOrders() == null)
					user.setCourierOrders(new ArrayList<Order>());
			}
			if (!user.getPassword().equals(found.getPassword()))
				user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			if (user.getUserStatus() == null)
				user.setUserStatus(found.getUserStatus());
			if (user.getUserType() == null)
				user.setUserType(found.getUserType());
			return userRepo.save(user);
		}
	}

	@Transactional
	@Override
	public User getById(User user) {
		userRepo.findById(user.getId());
		return null;
	}

	@Transactional
	@Override
	public User getByUsername(User user) {
		return userRepo.findByUsername(user.getUsername());
	}

	@Transactional
	@Override
	public User deleteUser(User user) {
		if (user == null)
			return null;
		User found = getByUsername(user);
		if (found == null) {
			return null;
		} else {
			found.setUserStatus(Status.DELETED);
			return userRepo.save(found);
		}
	}

	//methods needed for the customer
	//responsible for shopping cart operations
	@Override
	public ShoppingCart addToCart(User user, Product product, Integer quant) throws InvalidArgumentsException {
		Product found = productDao.getByName(product);
		if (quant < 1)
			throw new InvalidArgumentsException("The quantity should be a number greater than zero");
		if (found.getStock() < quant)
			throw new InvalidArgumentsException("The quantity exceeds the stock");
		removeFromCart(user, product);
		found.setStock(found.getStock() - quant);
		User u = getByUsername(user);
		ShoppingCart sc = u.addProductToCart(found, quant);
		updateUser(u);
		productDao.updateProduct(found);
		return sc;
	}

	@Override
	public ShoppingCart removeFromCart(User user, Product product) {
		if (product == null)
			return null;
		Product found = productDao.getByName(product);
		User u = getByUsername(user);
		Integer q = null;
		if (u.getShoppingCart().getProducts().contains(new ProductQuantity(product)))
			q = u.getShoppingCart().getProductQuantity(product);
		if (u.removeProduct(product))
			found.setStock(found.getStock() + q);
		updateUser(u);
		productDao.updateProduct(found);
		return u.getShoppingCart();
	}

	@Override
	public void discardCart(User user) {
		user.setShoppingCart(new ShoppingCart());
		updateUser(user);
	}

	@Override
	public void discardCartAndRestock(User user) {
		for (ProductQuantity p : user.getShoppingCart().getProducts()) {
			removeFromCart(user, p.getProduct());
		}
	}

	//responsible for order operations
	@Override
	public Order placeOrder(User user, Address address) {
		if (user == null)
			return null;
		if (address == null)
			return null;
		user = getByUsername(user);
		Order o = orderDao.createOrder(getByUsername(user), address);
		discardCart(user);
		return o;
	}

	@Override
	public ShoppingCart updateQuantityCart(User user, Product product, Integer quant) throws InvalidArgumentsException {
		Product found = productDao.getByName(product);
		// removeFromCart(user, product);
		found.setStock(found.getStock() - quant);
		return addToCart(user, product, quant);
	}

	@Override
	public ShoppingCart updateQuantityCart(User user, Integer[] quant) {
		User u = getByUsername(user);
		for (int i = 0; i < quant.length; i++) {
			int diff = -u.getShoppingCart().getProducts().get(i).getQuant() + quant[i];
			Product found = productDao.getByName(u.getShoppingCart().getProducts().get(i).getProduct());
			found.setStock(found.getStock() - diff);
			productDao.updateProduct(found);
			u.getShoppingCart().getProducts().get(i).setQuant(quant[i]);
		}
		updateUser(u);
		return u.getShoppingCart();
	}

	
	//method that updates the courer's order list
	@Override
	public User addCourierOrder(DistributionStrategy strategy, Order order) {
		List<User> couriers = getByRole(UserType.COURIER);
		User selected = strategy.selectCourier(order, couriers);
		selected.getCourierOrders().add(order);
		order.setCourierName(selected.getUsername());
		return updateUser(selected);
	}

}
