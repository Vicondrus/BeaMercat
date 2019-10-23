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

	public Integer getProductQuantity(Product product) {
		return products.stream().filter(x -> x.equals(new ProductQuantity(product, 0))).findFirst().get().getQuant();
	}

	public ShoppingCart addProductToCart(Product product, Integer quant) {
		ProductQuantity pq = new ProductQuantity(product, quant);
		if (products.contains(pq))
			products.remove(pq);
		products.add(pq);
		return this;
	}

	public boolean removeProduct(Product product) {
		return products.remove(new ProductQuantity(product));
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
