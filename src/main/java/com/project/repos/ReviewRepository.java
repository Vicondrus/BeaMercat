package com.project.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.entities.Review;

//data access object interface for review
//implementation provided by spring

public interface ReviewRepository extends MongoRepository<Review, String> {

	public List<Review> findByProductId(String productId);

	public Review findByReviewerUsernameAndProductId(String reviewerUsername, String productId);

}
