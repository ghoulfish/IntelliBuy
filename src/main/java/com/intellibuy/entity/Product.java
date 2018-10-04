package com.intellibuy.entity;

public class Product {
	
	private int productId;
	private int categoryCode;
	private String name;
	private String picUrl;
	private double price;
	private int onlineFlag;
	
	public Product(int productId, String name, double price) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getOnlineFlag() {
		return onlineFlag;
	}
	public void setOnlineFlag(int onlineFlag) {
		this.onlineFlag = onlineFlag;
	}

}
