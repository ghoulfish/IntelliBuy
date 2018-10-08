package com.intellibuy.entity;

public class ProductInCart extends EntityToDB{
	
	private Integer id;
	private Integer orderId;
	private Integer productId;
	private String name;
	private Integer number;
	private Integer price;
	
	public ProductInCart() {
		super();
	}

	public ProductInCart(int productId, String name, int number, int price) {
		super();
		this.productId = productId;
		this.name = name;
		this.number = number;
		this.price = price;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductInCart [orderId=" + orderId + ", productId=" + productId + ", name=" + name + ", number="
				+ number + ", price=" + price + "]";
	}
	
	
}
