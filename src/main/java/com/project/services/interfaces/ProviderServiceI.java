package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Provider;
import com.project.exceptions.InvalidArgumentsException;

public interface ProviderServiceI {

	List<Provider> getAll();

	Provider saveProvider(Provider provider) throws InvalidArgumentsException;

	Provider findByName(Provider provider);

	Provider findById(Provider provider);

	Provider updateProvider(Provider provider);

	Provider deleteProvider(Provider provider);

	List<Provider> getAllActive();

}
