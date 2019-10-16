package com.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Category;
import com.project.entities.Status;
import com.project.repos.CategoryRepository;
import com.project.services.interfaces.CategoryDaoI;

@Service
public class CategoryDao implements CategoryDaoI {

	@Autowired
	private CategoryRepository catRepo;

	@Transactional
	@Override
	public List<Category> getAll() {
		return catRepo.findAll();
	}

	@Transactional
	@Override
	public Category saveCategory(Category category) {
		if (category == null)
			return null;
		if (findByName(category) != null)
			return null;
		return catRepo.save(category);
	}

	@Transactional
	@Override
	public Category findByName(Category category) {
		return catRepo.findByName(category.getName());
	}

	@Transactional
	@Override
	public Category findById(Category category) {
		return catRepo.findById(category.getId()).get();
	}

	@Transactional
	@Override
	public Category updateCategory(Category category) {
		if (category == null)
			return null;
		Category found = findByName(category);
		if (found == null)
			return null;
		category.setId(found.getId());
		return catRepo.save(category);
	}

	@Transactional
	@Override
	public Category deleteCategory(Category category) {
		if (category == null)
			return null;
		Category found = findByName(category);
		if (found == null)
			return null;
		found.setCategoryStatus(Status.DELETED);
		return catRepo.save(category);
	}

}
