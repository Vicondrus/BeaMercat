package com.project.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<ProductQuantity> products;

	private Double totalPrice;

	public ShoppingCart() {
		this(new ArrayList<ProductQuantity>(), 0.0);
	}

	public ShoppingCart(List<ProductQuantity> products, Double totalPrice) {
		super();
		this.products = products;
		this.totalPrice = totalPrice;
	}

	public Integer getProductQuantity(Product product) {
		return products.stream().filter(x -> x.equals(new ProductQuantity(product, 0))).findFirst().get().getQuant();
	}

	public ShoppingCart addProductToCart(Product product, Integer quant) {
		ProductQuantity pq = new ProductQuantity(product, quant);
		if (products.contains(pq))
			products.remove(pq);
		products.add(pq);
		totalPrice += product.getPrice() * quant;
		return this;
	}

	public boolean removeProduct(Product product) {
		if (products.contains(new ProductQuantity(product))) {
			ProductQuantity pq = products.get(products.indexOf(new ProductQuantity(product)));
			totalPrice -= pq.getProduct().getPrice() * pq.getQuant();
		}
		return products.remove(new ProductQuantity(product));
	}

	public List<ProductQuantity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductQuantity> products) {
		this.products = products;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
