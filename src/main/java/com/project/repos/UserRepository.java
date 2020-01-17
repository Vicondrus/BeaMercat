package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.User;
import com.project.entities.UserType;

//data access object interface for user
//implementation provided by spring

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public User findByUsername(String username);
	
	public List<User> findByUserType(UserType userType);

}