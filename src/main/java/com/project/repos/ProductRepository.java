package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;

public interface ProductRepository extends MongoRepository<Product, String>{
	
	public Product findByName(String name);
	
	public List<Product> findAllByCategory(Category category);
	
	public List<Product> findAllByProvider(Provider provider);

}
