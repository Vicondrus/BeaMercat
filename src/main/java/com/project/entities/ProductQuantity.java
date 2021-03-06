package com.project.entities;

//helper class, not found in the database as a separate document
//used only to hold together in the shopping cart the product and its corresponding quantity

public class ProductQuantity {

	private Product product;
	
	private Integer quant;

	public ProductQuantity() {
		super();
	}
	
	//two such objects are equal if they represent the same product
	//quantity doesn't matter

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductQuantity other = (ProductQuantity) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.getName().equals(other.getProduct().getName()))
			return false;
		return true;
	}

	public ProductQuantity(Product product) {
		super();
		this.product = product;
	}

	public ProductQuantity(Product product, Integer quant) {
		super();
		this.product = product;
		this.quant = quant;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
	}
	
	
}
