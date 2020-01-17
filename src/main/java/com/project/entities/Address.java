package com.project.entities;

//POJO class that encompasses data related to addresses

public class Address {
	
	private String country;
	
	private String city;
	
	private String street;
	
	private Integer number;
	
	private Integer apartment;
	
	private String zipCode;

	public Address(String country, String city, String street, Integer number, Integer apartment, String zipCode) {
		super();
		this.country = country;
		this.city = city;
		this.street = street;
		this.number = number;
		this.apartment = apartment;
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getApartment() {
		return apartment;
	}

	public void setApartment(Integer apartment) {
		this.apartment = apartment;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
