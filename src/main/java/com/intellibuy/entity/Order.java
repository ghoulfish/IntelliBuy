package com.intellibuy.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends EntityToDB{
	
	private Integer id;
	private Integer customerId;
	private LocalDateTime createSince;
	private Integer discount = 0;
	private Boolean paid = false;
	private List<ProductInCart> products = new ArrayList<>();
	
	public Order() {
		super();
	}

	public Order(int id, int customerId) {
		super();
		this.id = id;
		this.customerId = customerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getCreateSince() {
		return createSince;
	}

	public void setCreateSince(LocalDateTime createSince) {
		this.createSince = createSince;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public List<ProductInCart> getProducts() {
		return products;
	}

	public void setProducts(List<ProductInCart> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", createSince=" + createSince + ", discount="
				+ discount + ", paid=" + paid + ", products=" + products + "]";
	}

}
