package com.project.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product implements Comparable<Product> {

	@Id
	private String id;

	private String name;

	private Double price;

	private String details;

	private Integer stock;

	private Category category;
	
	private Status productStatus;

	public Product() {
	}

	public Product(String id, String name, Double price, String details, Integer stock, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.stock = stock;
		this.category = category;
		this.productStatus = Status.ACTIVE;
	}

	public Product(String id, String name, Double price, String details, Integer stock, Category category,
			Status productStatus) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.stock = stock;
		this.category = category;
		this.productStatus = productStatus;
	}

	public Status getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Status productStatus) {
		this.productStatus = productStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int compareTo(Product o) {
		if (o == null)
			return 0;
		if (this.name == null)
			return 0;
		if (o.name == null)
			return 0;
		return (this.name.compareTo(o.getName()));
	}

}
