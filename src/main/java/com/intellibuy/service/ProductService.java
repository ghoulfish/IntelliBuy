package com.intellibuy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intellibuy.entity.Product;
import com.intellibuy.entity.ProductInCart;

@Service
public class ProductService {
	
	@Autowired
	private CookieService cookieService;
	@Autowired
	private JsonService jsonService;
	@Autowired
	private JdbcService jdbcService;
	
	public List<Product> findAll() {
		return jdbcService.findAll(new Product());
	}
	
	public Product findById( int id ) {
		return jdbcService.findProductById(id);
	}

	public String addToCartCookieValue(String cartCookieValue, int id, String name, int price, int number) throws IOException {
		ProductInCart prod;
		Map<Integer, ProductInCart> productMap;
		if ( cartCookieValue.equals("") ) {
			productMap = new HashMap<>();
			prod = new ProductInCart(id, name, number, price);
			productMap.put(id, prod);
		} else {
			productMap = jsonService.getCartProductMap(cartCookieValue);
			prod = productMap.getOrDefault(id, new ProductInCart(id, name, 0, price));
			prod.setNumber(number + prod.getNumber());
			productMap.put(id, prod);
		}
		return jsonService.objectToCookieValue(productMap) ;
	}
	
	public void addProductInCart(HttpServletRequest request, HttpServletResponse response,
			ProductInCart productInCart) {
		Map<Integer, ProductInCart> productMap;
		if (cookieService.getCookie(request, "cart") == null) {
			productMap = new HashMap<>();
		} else {
			productMap=jsonService.getCartProductMap(cookieService.getCookie(request, "cart").getValue());
			if (productMap.get(productInCart.getProductId()) != null) {
				int num = productMap.get(productInCart.getProductId()).getNumber();
				productInCart.setNumber(productInCart.getNumber() + num);
			}
		}
		productMap.put(productInCart.getProductId(), productInCart);
		try {
			cookieService.createCookie(response, "cart", jsonService.objectToCookieValue(productMap), "/", 60 * 60 * 7);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProductInCart (HttpServletRequest request, HttpServletResponse response, int productId) {
		String cartCookieValue = cookieService.getCookie(request, "cart").getValue();
		Map<Integer, ProductInCart> productMap = jsonService.getCartProductMap(cartCookieValue);
		productMap.remove(productId);
		if (productMap.isEmpty()) {
			cookieService.createCookie(response, "cart", "", "/", 0);
		} else {
			try {
				cookieService.createCookie(response, "cart", jsonService.objectToCookieValue(productMap), "/", 60 * 60 * 7 );
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	public List<ProductInCart> getCartProductList(String cartCookieValue) {
		if ( cartCookieValue.equals("") ) {
			return null;
		}
		Map<Integer, ProductInCart> productMap = jsonService.getCartProductMap(cartCookieValue);
		for (ProductInCart prod: productMap.values()) {
			System.out.println(prod);
		}
		return new ArrayList<ProductInCart>(productMap.values());
	}
	
	public void renewCartCookie(String newCookieValue, HttpServletResponse response) {
		cookieService.createCookie(response, "cart", newCookieValue, "/", 60 * 60 * 24);
	}
	
	public void deleteCartCookie(HttpServletRequest request, HttpServletResponse response) {
		cookieService.delete(response, cookieService.getCookie(request, "cart"));
	}

}
