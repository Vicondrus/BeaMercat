package com.project.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//used to hold data about the review
//document in the database
//productId and reviewerUsername link this document with other relevant documents from
//the database

@Document
public class Review {

	@Id
	private String id;

	private String productId;

	private String reviewerUsername;

	private String details;

	private Integer mark;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public Review() {

	}

	public Review(String id, String productId, String reviewerUsername, String details, Integer mark) {
		super();
		this.id = id;
		this.productId = productId;
		this.reviewerUsername = reviewerUsername;
		this.details = details;
		this.mark = mark;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getReviewerUsername() {
		return reviewerUsername;
	}

	public void setReviewerUsername(String reviewerUsername) {
		this.reviewerUsername = reviewerUsername;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}
}
