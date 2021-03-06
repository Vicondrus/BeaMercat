package com.project.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//document, found in the database
//entity that holds relevant data for a user
//note that the tree types of users are merged into one
//the database will not hold null fields (ex: an admin will have a 
//null shoppingcart and orders list which will not appear in the databases)

@Document
public class User {

	@Id
	private String id;

	private String username;

	private String firstName;

	private String lastName;

	private String password;

	private String email;

	private Address address;

	private ShoppingCart shoppingCart;

	private UserType userType;

	private Status userStatus;

	private String telephone;

	private List<Order> courierOrders;

	public User() {
	}

	public List<Order> getCourierOrders() {
		return courierOrders;
	}

	public void setCourierOrders(List<Order> courierOrders) {
		this.courierOrders = courierOrders;
	}

	public User(String id, String username, String firstName, String lastName, String password, String email,
			String telephone) {
		super();
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.username = username;
		this.email = email;
		this.shoppingCart = null;
		this.userType = UserType.CUSTOMER;
		this.userStatus = Status.ACTIVE;
		this.telephone = telephone;
	}

	public User(String id, String username, String firstName, String lastName, String password, String email,
			Address address, ShoppingCart shoppingCart, UserType userType, String telephone) {
		super();
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.username = username;
		this.email = email;
		this.address = address;
		this.shoppingCart = shoppingCart;
		this.userType = userType;
		this.userStatus = Status.ACTIVE;
		this.telephone = telephone;
	}

	public User(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ShoppingCart addProductToCart(Product product, Integer quant) {
		return shoppingCart.addProductToCart(product, quant);
	}

	public boolean removeProduct(Product product) {
		return shoppingCart.removeProduct(product);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + "]";
	}

	public List<Order> getProcessingOrders() {
		return courierOrders.stream().filter(x -> x.getStatus().equals(OrderStatus.PROCESSING))
				.collect(Collectors.toList());
	}

}