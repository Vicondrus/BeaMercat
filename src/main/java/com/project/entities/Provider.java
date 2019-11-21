package com.project.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Provider {

	@Id
	private String id;

	private String name;

	private String telephone;

	private String email;

	private Address address;

	private Status providerStatus;

	public Provider(String name) {
		this.name = name;
	}

	public Provider() {

	}

	public Provider(String id, String name, String phone, Address address, Status status, String email) {
		super();
		this.id = id;
		this.name = name;
		this.telephone = phone;
		this.address = address;
		providerStatus = status;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(Status providerStatus) {
		this.providerStatus = providerStatus;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String phone) {
		this.telephone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
