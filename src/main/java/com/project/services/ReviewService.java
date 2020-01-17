package com.project.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Product;
import com.project.entities.Review;
import com.project.entities.User;
import com.project.repos.ReviewRepository;
import com.project.services.interfaces.ReviewServiceI;

//service class that wraps the data access for review
//and provides application logic

@Service
public class ReviewService implements ReviewServiceI {

	@Autowired
	private ReviewRepository revRepo;

	@Override
	public List<Review> getAllActiveForProduct(Product product) {
		if (product == null)
			return null;
		return revRepo.findByProductId(product.getId());
	}

	@Override
	public Review saveReview(Review review) {
		if (review == null)
			return null;
		review.setDate(new Date());
		return revRepo.save(review);
	}

	@Override
	public Review updateReview(Review review) {
		if (review == null)
			return null;
		Review found = revRepo.findById(review.getId()).orElse(null);
		if (found == null)
			return null;
		found.setDetails(review.getDetails());
		found.setMark(review.getMark());
		found.setDate(new Date());
		return revRepo.save(found);
	}

	@Override
	public Review findCustomerReview(User user, List<Review> reviews) {
		for (Review r : reviews)
			if (r.getReviewerUsername().equals(user.getUsername()))
				return r;
		return null;
	}

	@Override
	public Review getById(Review review) {
		return revRepo.findById(review.getId()).orElse(null);
	}

	@Override
	public Review deleteReview(Review review) {
		if (review == null)
			return null;
		revRepo.delete(review);
		return review;
	}

	@Override
	public List<Review> getAll() {
		return revRepo.findAll();
	}

}
