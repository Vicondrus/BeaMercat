package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Provider;

public interface ProviderServiceI {

	List<Provider> getAll();

	Provider saveProvider(Provider provider);

	Provider findByName(Provider provider);

	Provider findById(Provider provider);

	Provider updateProvider(Provider provider);

	Provider deleteProvider(Provider provider);

	List<Provider> getAllActive();

}