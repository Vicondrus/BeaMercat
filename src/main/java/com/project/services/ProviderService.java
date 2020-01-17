package com.project.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;
import com.project.exceptions.InvalidArgumentsException;
import com.project.repos.ProviderRepository;
import com.project.services.interfaces.ProviderServiceI;

//service class that wraps the data access for provider
//and provides application logic

@Service
public class ProviderService implements ProviderServiceI {

	//auto instantiated beans
	@Autowired
	private ProviderRepository provRepo;

	@Autowired
	private ProductService prodDao;
	
	//methods for adding, deleting, updating and retrieving providers from the database
	//with additional, corresponding application logic

	//checks if a given phone number has a valid format
	private boolean isValidPhone(String s) {
		Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

		Matcher m = p.matcher(s);
		return (m.find() && m.group().equals(s));
	}

	@Transactional
	@Override
	public List<Provider> getAll() {
		return provRepo.findAll();
	}

	@Transactional
	@Override
	public List<Provider> getAllActive() {
		return provRepo.findByProviderStatus(Status.ACTIVE);
	}

	@Transactional
	@Override
	public Provider saveProvider(Provider provider) throws InvalidArgumentsException {
		if (provider == null)
			return null;
		if (findByName(provider) != null)
			throw new InvalidArgumentsException("Provider already exists");
		if(!isValidPhone(provider.getTelephone()))
			throw new InvalidArgumentsException("Telehpne not valid");
		return provRepo.save(provider);
	}

	@Transactional
	@Override
	public Provider findByName(Provider provider) {
		return provRepo.findByName(provider.getName());
	}

	@Transactional
	@Override
	public Provider findById(Provider provider) {
		return provRepo.findById(provider.getId()).get();
	}

	@Transactional
	@Override
	public Provider updateProvider(Provider provider) {
		if (provider == null)
			return null;
		Provider found = findByName(provider);
		if (found == null)
			return null;
		List<Product> prods = prodDao.getAllByProvider(found);
		provider.setId(found.getId());
		prods.stream().forEach(x -> {
			x.setProvider(provider);
			prodDao.updateProduct(x);
		});
		return provRepo.save(provider);
	}

	@Transactional
	@Override
	public Provider deleteProvider(Provider provider) {
		if (provider == null)
			return null;
		Provider found = findByName(provider);
		if (found == null)
			return null;
		found.setProviderStatus(Status.DELETED);
		return provRepo.save(found);
	}

}
