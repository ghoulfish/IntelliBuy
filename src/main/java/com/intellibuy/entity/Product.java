package com.intellibuy.entity;

import java.time.LocalDateTime;

public class Product extends EntityToDB{
	
	private Integer id;
	private Integer categoryCode;
	private String name;
	private String picUrl;
	private Integer price;
	private Integer stock;
	private Integer onlineFlag;
	private LocalDateTime createSince;
	private LocalDateTime updateSince;
	
	public Product() {
		super();
	}
	public Product(String name, int price, int stock) {
		super();
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(Integer categoryCode) {
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getOnlineFlag() {
		return onlineFlag;
	}
	public void setOnlineFlag(Integer onlineFlag) {
		this.onlineFlag = onlineFlag;
	}
	
	public LocalDateTime getCreateSince() {
		return createSince;
	}
	public void setCreateSince(LocalDateTime createSince) {
		this.createSince = createSince;
	}
	public LocalDateTime getUpdateSince() {
		return updateSince;
	}
	public void setUpdateSince(LocalDateTime updateSince) {
		this.updateSince = updateSince;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", categoryCode=" + categoryCode + ", name=" + name + ", picUrl=" + picUrl
				+ ", price=" + price + ", stock=" + stock + ", onlineFlag=" + onlineFlag + ", createSince="
				+ createSince + ", updateSince=" + updateSince + "]";
	}
}
