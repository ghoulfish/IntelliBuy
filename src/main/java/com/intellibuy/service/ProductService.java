package com.intellibuy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intellibuy.entity.Product;
import com.intellibuy.entity.data.ProductData;

@Service
public class ProductService {
	
	public List<Product> findAll() {
		return ProductData.getInstance().findAll();
	}
	
	public Product findById( int id ) {
		return ProductData.getInstance().findById(id);
	}

}
