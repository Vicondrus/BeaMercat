package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Status;
import com.project.repos.CategoryRepository;
import com.project.services.interfaces.CategoryServiceI;

@Service
public class CategoryService implements CategoryServiceI {

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductService prodDao;

	@Transactional
	@Override
	public List<Category> getAll() {
		return catRepo.findAll();
	}

	@Transactional
	@Override
	public List<Category> getAllActive() {
		return catRepo.findByCategoryStatus(Status.ACTIVE);
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
		List<Product> prods = prodDao.getAllByCategory(found);
		category.setId(found.getId());
		prods.stream().forEach(x -> {
			x.setCategory(category);
			prodDao.updateProduct(x);
		});
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
		return catRepo.save(found);
	}

}
