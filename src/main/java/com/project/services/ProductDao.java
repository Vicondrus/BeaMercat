package com.project.services;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;
import com.project.repos.ProductRepository;
import com.project.services.interfaces.CategoryDaoI;
import com.project.services.interfaces.ProductDaoI;
import com.project.services.interfaces.ProviderDaoI;

@Service
public class ProductDao implements ProductDaoI {

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private CategoryDaoI catDao;

	@Autowired
	private ProviderDaoI provDao;

	@Override
	public Product saveProductWithImage(Product product, MultipartFile file) throws IOException {
		if (product == null)
			return null;
		if (getByName(product) != null)
			return null;
		if (product.getProductStatus() == null)
			product.setProductStatus(Status.ACTIVE);
		product.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		return prodRepo.save(product);
	}

	@Override
	public Product saveProduct(Product product) {
		if (product == null)
			return null;
		if (getByName(product) != null)
			return null;
		if (product.getProductStatus() == null)
			product.setProductStatus(Status.ACTIVE);
		return prodRepo.save(product);
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
	public Product updateProductImage(Product product, MultipartFile file) throws IOException {
		if (file == null)
			return updateProduct(product);
		if (product == null)
			return null;
		Product found = getByName(product);
		if (found == null) {
			return null;
		} else {
			product.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
			product.setId(found.getId());
			return prodRepo.save(product);
		}
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
		if (fCat == null)
			return null;
		return prodRepo.findAllByCategory(fCat);
	}

	@Override
	public List<Product> getAllByProvider(Provider provider) {
		if (provider == null)
			return null;
		Provider fProv = provDao.findByName(provider);
		if (fProv == null)
			return null;
		return prodRepo.findAllByProvider(fProv);
	}

	@Transactional
	@Override
	public List<Product> getAllActive() {
		return prodRepo.findByProductStatus(Status.ACTIVE);
	}
}
