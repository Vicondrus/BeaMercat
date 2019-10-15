package com.project.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product implements Comparable<Product> {

	@Id
	private String Id;

	private String name;

	private Double price;

	private String details;

	private Integer stock;

	private Category category;

	public Product() {
	}

	public Product(String id, String name, Double price, String details, Integer stock, Category category) {
		super();
		Id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.stock = stock;
		this.category = category;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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
