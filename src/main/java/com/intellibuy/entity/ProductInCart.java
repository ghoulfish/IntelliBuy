package com.intellibuy.entity;

public class ProductInCart {
	
	private int orderId;
	private int productId;
	private String name;
	private int number;
	private double price;
	
	public ProductInCart() {
		super();
	}

	public ProductInCart(int productId, String name, int number, double price) {
		super();
		this.productId = productId;
		this.name = name;
		this.number = number;
		this.price = price;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductInCart [orderId=" + orderId + ", productId=" + productId + ", name=" + name + ", number="
				+ number + ", price=" + price + "]";
	}
}
