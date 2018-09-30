package com.intellibuy.entity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intellibuy.entity.Product;

public class ProductData {
	
	private Map<Integer, Product> productById;
	
	private static final ProductData INSTANCE = new ProductData();

	public static ProductData getInstance() {
		return INSTANCE;
	}
	
	public ProductData() {
		
		productById = new HashMap<>();
		this.productById.put(1, new Product(1, "Bed", 199.9));
		this.productById.put(2, new Product(2, "Pillow", 39.9));
		this.productById.put(3, new Product(3, "Lamp", 19.9));
		this.productById.put(4, new Product(4, "Door", 159.9));
	}
	
	public List<Product> findAll() {
		return new ArrayList(this.productById.values());
	}
	
	public Product findById(int id) {
		return this.productById.get(id);
	}

}
