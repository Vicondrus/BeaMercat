package com.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Product;
import com.project.entities.ShoppingCart;
import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.repos.UserRepository;
import com.project.services.interfaces.UserDaoI;

@Service
public class UserDao implements UserDaoI {
	@Autowired
	UserRepository userRepo;

	@Transactional
	@Override
	public User saveUser(User user) {
		if (user == null)
			return null;
		if (userRepo.findByUsername(user.getUsername()) != null)
			return null;
		if(user.getUserType() == null)
			user.setUserType(UserType.CUSTOMER);
		if (user.getUserType().equals(UserType.CUSTOMER) && user.getShoppingCart() == null)
			user.setShoppingCart(new ShoppingCart());
		user.setUserStatus(Status.ACTIVE);
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
		userRepo.findByUsername(user.getUsername());
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCart removeFromCart(User user, Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCart updateQuantityCart(User user, Product product, Integer quant) {
		// TODO Auto-generated method stub
		return null;
	}

}
