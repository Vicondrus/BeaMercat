package com.project.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	public Category findByName(String name);

}
