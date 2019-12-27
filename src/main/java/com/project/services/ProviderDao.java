package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.Product;
import com.project.entities.Provider;
import com.project.entities.Status;
import com.project.repos.ProviderRepository;
import com.project.services.interfaces.ProviderDaoI;

@Service
public class ProviderDao implements ProviderDaoI {

	@Autowired
	private ProviderRepository provRepo;

	@Autowired
	private ProductDao prodDao;

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
	public Provider saveProvider(Provider provider) {
		if (provider == null)
			return null;
		if (findByName(provider) != null)
			return null;
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
