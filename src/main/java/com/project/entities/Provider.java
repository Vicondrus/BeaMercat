package com.project.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Provider {

	@Id
	private String id;

	private String name;

	private String phone;

	private Address address;

	private Status providerStatus;

	public Provider() {

	}

	public Provider(String id, String name, String phone, Address address, Status status) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		providerStatus = status;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
