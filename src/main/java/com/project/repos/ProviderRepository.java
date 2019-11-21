package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.Category;
import com.project.entities.Provider;
import com.project.entities.Status;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {

	public Provider findByName(String name);
	
	public List<Provider> findByProviderStatus(Status status);

}
