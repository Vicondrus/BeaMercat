package com.project.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String id;

	private String name;

	private String email;
	
	private Address address;
	
	private ShoppingCart shoppingCart;
	
	private UserType userType;
	
	public User() {
	}
	
	public User(String id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.shoppingCart = null;
		this.userType = UserType.ADMIN;
	}

	public User(String id, String name, String email, Address address, ShoppingCart shoppingCart, UserType userType) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.shoppingCart = shoppingCart;
		this.userType = userType;
	}
	
	public void addProductToCart(Product product, Integer quant) {
		shoppingCart.addProductToCart(product, quant);
	}
	
	public void removeProduct(Product product) {
		shoppingCart.removeProduct(product);
	}


	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}


	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + "]";
	}

}