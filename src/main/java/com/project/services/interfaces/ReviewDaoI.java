package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Product;
import com.project.entities.Review;
import com.project.entities.User;

public interface ReviewDaoI {

	List<Review> getAllActiveForProduct(Product product);

	Review saveReview(Review review);

	Review updateReview(Review review);

	Review deleteReview(Review review);

	List<Review> getAll();

	Review findCustomerReview(User user, List<Review> reviews);

	Review getById(Review review);

}
