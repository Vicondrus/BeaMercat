package com.project.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUsername(String username);

}