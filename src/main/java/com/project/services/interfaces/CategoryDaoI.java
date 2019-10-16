package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Category;

public interface CategoryDaoI {
	
	public Category saveCategory(Category category);
	
	public Category findByName(Category category);
	
	public Category findById(Category category);
	
	public Category updateCategory(Category category);
	
	public Category deleteCategory(Category category);

	List<Category> getAll();

}
