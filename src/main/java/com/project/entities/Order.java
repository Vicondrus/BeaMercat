package com.project.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

	@Id
	private String id;

	private String customerUsername;

	private User customer;

	private Address address;

	private List<ProductQuantity> products;

	private OrderStatus status;

	private Date createdDate;

	private Double total;

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Order() {

	}

	public Order(User customer, Address address) {
		this.customer = customer;
		products = customer.getShoppingCart().getProducts();
		this.address = address;
		this.customerUsername = customer.getUsername();
		createdDate = new Date();
		total = customer.getShoppingCart().getTotalPrice();
	}

	public Order(String id) {
		this.id = id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<ProductQuantity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductQuantity> products) {
		this.products = products;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

}
