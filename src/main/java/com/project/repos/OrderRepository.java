package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Order;

public interface OrderRepository extends MongoRepository<Order, String>{
	
	public List<Order> findByCustomerUsername(String user);
	
}
