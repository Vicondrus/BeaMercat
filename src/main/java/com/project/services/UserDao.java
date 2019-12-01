package com.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.Product;
import com.project.entities.ProductQuantity;
import com.project.entities.ShoppingCart;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.repos.UserRepository;
import com.project.services.interfaces.OrderDaoI;
import com.project.services.interfaces.ProductDaoI;
import com.project.services.interfaces.UserDaoI;

@Service
public class UserDao implements UserDaoI {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductDaoI productDao;

	@Autowired
	private OrderDaoI orderDao;

	@Transactional
	@Override
	public User saveUser(User user) {
		if (user == null)
			return null;
		user.setUsername(user.getUsername().trim());
		if (userRepo.findByUsername(user.getUsername()) != null)
			return null;
		if (user.getUserType() == null)
			user.setUserType(UserType.CUSTOMER);
		if (user.getUserType().equals(UserType.CUSTOMER) && user.getShoppingCart() == null)
			user.setShoppingCart(new ShoppingCart());
		user.setUserStatus(Status.ACTIVE);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
		return userRepo.save(user);
	}

	public List<User> getAll() {
		return userRepo.findAll();
	}

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

	@Override
	public ShoppingCart addToCart(User user, Product product, Integer quant) {
		Product found = productDao.getByName(product);
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
	public ShoppingCart updateQuantityCart(User user, Product product, Integer quant) {
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

}
