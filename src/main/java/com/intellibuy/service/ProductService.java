package com.intellibuy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.Order;
import com.intellibuy.entity.Product;
import com.intellibuy.entity.ProductInCart;
import com.intellibuy.entity.data.ProductData;

@Service
public class ProductService {
	
	@Autowired
	private CookieService cookieService;
	@Autowired
	private JsonService jsonService;
	
	public List<Product> findAll() {
		return ProductData.getInstance().findAll();
	}
	
	public Product findById( int id ) {
		return ProductData.getInstance().findById(id);
	}

	public String addToCartCookieValue(String cartCookieValue, int id, String name, double price, int number) throws IOException {
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
			ProductInCart productInCart) throws IOException {
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
		cookieService.createCookie(response, "cart", jsonService.objectToCookieValue(productMap), "/", 60 * 60 * 7);
	}
	
	public void deleteProductInCart (HttpServletRequest request, HttpServletResponse response, int productId) throws IOException {
		String cartCookieValue = cookieService.getCookie(request, "cart").getValue();
		Map<Integer, ProductInCart> productMap = jsonService.getCartProductMap(cartCookieValue);
		productMap.remove(productId);
		if (productMap.isEmpty()) {
			cookieService.createCookie(response, "cart", "", "/", 0);
		} else {
			cookieService.createCookie(response, "cart", jsonService.objectToCookieValue(productMap), "/", 60 * 60 * 7 );
		}
	}

	public List<ProductInCart> getCartProductList(String cartCookieValue) throws IOException {
		if ( cartCookieValue.equals("") ) {
			return null;
		}
		Map<Integer, ProductInCart> productMap = jsonService.getCartProductMap(cartCookieValue);
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
		cookieService.createCookie(response, "cart", newCookieValue, "/", 60 * 60 * 24);
	}
	
	public void deleteCartCookie(HttpServletRequest request, HttpServletResponse response) {
		cookieService.delete(response, cookieService.getCookie(request, "cart"));
	}

}
