package com.project.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;

public interface ProductDaoI {

	public Product saveProduct(Product product);

	public Product getByName(Product product);

	public Product getById(Product product);

	public Product updateProduct(Product product);

	public Product deleteProduct(Product product);

	public List<Product> getAllByCategory(Category cat);

	List<Product> getAll();

	Product saveProductWithImage(Product product, MultipartFile file) throws IOException;

	Product updateProductImage(Product product, MultipartFile file) throws IOException;

	List<Product> getAllByProvider(Provider provider);

	List<Product> getAllActive();

}
