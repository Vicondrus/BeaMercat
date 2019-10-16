package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Category;
import com.project.entities.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	
	public Product findByName(String name);
	
	public List<Product> findAllByCategory(Category category);

}
