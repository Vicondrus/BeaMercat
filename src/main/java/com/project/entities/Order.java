package com.project.entities;

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
		products.addAll(customer.getShoppingCart().getProducts());
		this.address = address;
		this.customerUsername = customer.getUsername();
	}

	public Order(User customer) {
		this(customer, customer.getAddress());
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
