package com.intellibuy.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private int orderId;
	private int customerId;
	private List<ProductInCart> products = new ArrayList<>();
	
	public Order() {
		super();
	}

	public Order(int orderId, int customerId) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<ProductInCart> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerId=" + customerId + ", products=" + products + "]";
	}

}
