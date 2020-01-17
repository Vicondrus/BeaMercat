package com.project.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Category;
import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;
import com.project.repos.ProductRepository;
import com.project.services.interfaces.CategoryServiceI;
import com.project.services.interfaces.ProductServiceI;
import com.project.services.interfaces.ProviderServiceI;

//service class that wraps the data access for product
//and provides application logic

@Service
public class ProductService implements ProductServiceI {

	//auto instantiated beans
	//repository bean
	@Autowired
	private ProductRepository prodRepo;

	//service beans
	@Autowired
	private CategoryServiceI catDao;

	@Autowired
	private ProviderServiceI provDao;
	
	//methods for adding, deleting, updating and retrieving products from the database
	//with additional, corresponding application logic

	
	//method for saving a product that also has an image accompanying it
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

	//save a product without an image
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

	//method that updates a product if an image is involved
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
			if (file == null || file.isEmpty())
				product.setImage(found.getImage());
			else
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
	
	
	//method that retrieves, using a repo, products containing in their name
	//a certain string
	@Override
	public List<Product> getAllActiveByNameLike(Product product) {
		if (product == null)
			return null;
		String name = Arrays.stream(product.getName().split("\\s+"))
				.map(t -> t.substring(0, 1).toUpperCase() + t.substring(1)).collect(Collectors.joining(" "));
		return prodRepo.findByNameLikeAndProductStatus(name, Status.ACTIVE);
	}

	@Override
	public List<Product> getAllActiveByCatergory(Category category) {
		if (category == null)
			return null;
		Category fCat = catDao.findByName(category);
		if (fCat == null)
			return null;
		return prodRepo.findAllByCategoryAndProductStatus(fCat, Status.ACTIVE);
	}

	@Override
	public List<Product> getAllActiveByProvider(Provider provider) {
		if (provider == null)
			return null;
		Provider fProv = provDao.findByName(provider);
		if (fProv == null)
			return null;
		return prodRepo.findAllByProviderAndProductStatus(fProv, Status.ACTIVE);
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
