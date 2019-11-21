package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Category;
import com.project.entities.Status;

public interface CategoryRepository extends MongoRepository<Category, String> {

	public Category findByName(String name);
	
	public List<Category> findByCategoryStatus(Status status);

}
