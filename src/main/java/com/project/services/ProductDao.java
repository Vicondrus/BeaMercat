package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Status;
import com.project.repos.ProductRepository;
import com.project.services.interfaces.CategoryDaoI;
import com.project.services.interfaces.ProductDaoI;

@Service
public class ProductDao implements ProductDaoI {

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private CategoryDaoI catDao;

	@Override
	public Product saveProduct(Product product) {
		if (product == null)
			return null;
		if (getByName(product) != null)
			return null;
		if (product.getProductStatus() == null)
			product.setProductStatus(Status.ACTIVE);
		return null;
	}

	@Override
	public Product getByName(Product product) {
		if (product == null)
			return null;
		return prodRepo.findByName(product.getName());
	}

	@Override
	public Product getById(Product product) {
		if (product == null)
			return null;
		return prodRepo.findById(product.getId()).orElse(null);
	}

	@Override
	public Product updateProduct(Product product) {
		if (product == null)
			return null;
		Product found = getByName(product);
		if (found == null) {
			return null;
		} else {
			
			product.setId(found.getId());
			return prodRepo.save(product);
		}
	}

	@Override
	public Product deleteProduct(Product product) {
		if (product == null)
			return null;
		Product found = getByName(product);
		if (found == null) {
			return null;
		} else {
			found.setProductStatus(Status.DELETED);
			return prodRepo.save(found);
		}
	}

	@Override
	public List<Product> getAll() {
		return prodRepo.findAll();
	}

	@Override
	public List<Product> getAllByCategory(Category cat) {
		if (cat == null)
			return null;
		Category fCat = catDao.findByName(cat);
		if(fCat == null)
			return null;
		return prodRepo.findAllByCategory(fCat);
	}

}
