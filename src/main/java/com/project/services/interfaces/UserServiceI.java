package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.Product;
import com.project.entities.ShoppingCart;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.exceptions.InvalidArgumentsException;
import com.project.services.distributionstrategies.DistributionStrategy;

public interface UserServiceI {

	User saveUser(User user) throws InvalidArgumentsException;
	
	User updateUser(User user);
	
	User deleteUser(User user);

	List<User> getAll();

	User getById(User user);

	User getByUsername(User user);
	
	ShoppingCart addToCart(User user, Product product, Integer quant) throws InvalidArgumentsException;
	
	ShoppingCart removeFromCart(User user, Product product);
	
	ShoppingCart updateQuantityCart(User user, Product product, Integer quant) throws InvalidArgumentsException;

	void discardCart(User user);

	Order placeOrder(User user, Address address);

	ShoppingCart updateQuantityCart(User u, Integer[] quant);

	void discardCartAndRestock(User user);

	List<User> getByRole(UserType role);

	User addCourierOrder(DistributionStrategy strategy, Order order);

}
