package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;

public interface ProductRepository extends MongoRepository<Product, String> {

	public Product findByName(String name);

	public List<Product> findAllByCategory(Category category);

	public List<Product> findAllByProvider(Provider provider);

	public List<Product> findAllByCategoryAndProductStatus(Category category, Status status);

	public List<Product> findAllByProviderAndProductStatus(Provider provider, Status status);

	public List<Product> findByProductStatus(Status status);

	public List<Product> findByNameLikeAndProductStatus(String likeName, Status status);

	public List<Product> findByNameLike(String likeName);

}
