package com.intellibuy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellibuy.entity.Order;
import com.intellibuy.entity.Product;
import com.intellibuy.entity.ProductInCart;
import com.intellibuy.entity.data.ProductData;

@Service
public class ProductService {
	
	public List<Product> findAll() {
		return ProductData.getInstance().findAll();
	}
	
	public Product findById( int id ) {
		return ProductData.getInstance().findById(id);
	}

	public String addToCartCookieValue(String cartCookieValue, int id, String name, double price, int number) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ProductInCart prod;
		Map<Integer, ProductInCart> productList;
		if ( cartCookieValue.equals("") ) {
			productList = new HashMap<>();
			prod = new ProductInCart(id, name, number, price);
			productList.put(id, prod);
		} else {
			productList = objectMapper.readValue( Base64.getDecoder().decode(cartCookieValue) , new TypeReference<Map<Integer, ProductInCart>>(){} );
			prod = productList.getOrDefault(id, new ProductInCart(id, name, 0, price));
			prod.setNumber(number + prod.getNumber());
			productList.put(id, prod);
		}
		return Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(productList).getBytes()) ;
	}

	public List<ProductInCart> getCartProductList(String cartCookieValue) throws IOException {
		if ( cartCookieValue.equals("") ) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Map<Integer, ProductInCart> productMap = objectMapper.readValue(Base64.getDecoder().decode(cartCookieValue.getBytes()), new TypeReference<Map<Integer, ProductInCart>>(){});
		return new ArrayList<ProductInCart>(productMap.values());
	}

	public Order createOrder(HttpServletRequest request) throws IOException {
		for (Cookie cookie: request.getCookies()) {
			if (cookie.getName().equals("cart")) {
				List<ProductInCart> productList = getCartProductList(cookie.getValue());
				Order order = new Order();
				for (ProductInCart prod: productList) {
					order.getProducts().add(prod);
				}
				return order;
			}
		}
		return null;
	}
	
	public void renewCartCookie(String newCookieValue, HttpServletResponse response) {
		Cookie cartCookie = new Cookie("cart", newCookieValue);
		cartCookie.setMaxAge(60*60*24);
		cartCookie.setPath("/");
		response.addCookie(cartCookie);
	}
	
	public void deleteCartCookie(HttpServletRequest request, HttpServletResponse response) {
		for (Cookie cookie: request.getCookies()) {
			if (cookie.getName().equals("cart")) {
				cookie.setValue("");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				return;
			}
		}
	}

}
