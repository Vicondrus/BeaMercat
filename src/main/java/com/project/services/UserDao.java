package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.User;
import com.project.repos.UserRepository;
import com.project.services.interfaces.UserDaoI;

@Service
public class UserDao implements UserDaoI {
	@Autowired
	UserRepository userRepo;

	public User insert(User user) {
		if (user == null)
			return null;
		if (userRepo.findByName(user.getName()) != null)
			return null;
		return userRepo.save(user);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

}
