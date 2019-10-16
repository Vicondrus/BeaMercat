package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Product;
import com.project.entities.ShoppingCart;
import com.project.entities.User;

public interface UserDaoI {

	User saveUser(User user);
	
	User updateUser(User user);
	
	User deleteUser(User user);

	List<User> getAll();

	User getById(User user);

	User getByUsername(User user);
	
	ShoppingCart addToCart(User user, Product product, Integer quant);
	
	ShoppingCart removeFromCart(User user, Product product);
	
	ShoppingCart updateQuantityCart(User user, Product product, Integer quant);

}
