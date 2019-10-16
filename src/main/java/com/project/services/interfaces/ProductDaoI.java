package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Category;
import com.project.entities.Product;

public interface ProductDaoI {

	public Product saveProduct(Product product);

	public Product getByName(Product product);

	public Product getById(Product product);

	public Product updateProduct(Product product);

	public Product deleteProduct(Product product);

	public List<Product> getAllByCategory(Category cat);

	List<Product> getAll();

}
