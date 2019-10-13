package com.project.services.interfaces;

import java.util.List;

import com.project.entities.User;

public interface UserDaoI {

	User insert(User user);

	List<User> findAll();

}
