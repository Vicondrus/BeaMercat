package com.project.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.Provider;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {

	public Provider findByName(String name);

}
