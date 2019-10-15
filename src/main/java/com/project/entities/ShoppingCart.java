package com.project.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<ProductQuantity> products;

	private Integer totalPrice;

	public ShoppingCart() {
		this(new ArrayList<ProductQuantity>(), 0);
	}

	public ShoppingCart(List<ProductQuantity> products, Integer totalPrice) {
		super();
		this.products = products;
		this.totalPrice = totalPrice;
	}

	public void addProductToCart(Product product, Integer quant) {
		products.add(new ProductQuantity(product, quant));
	}

	public void removeProduct(Product product) {
		products.remove(new ProductQuantity(product));
	}

	public List<ProductQuantity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductQuantity> products) {
		this.products = products;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

}
