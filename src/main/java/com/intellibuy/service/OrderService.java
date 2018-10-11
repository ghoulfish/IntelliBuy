package com.intellibuy.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.Order;
import com.intellibuy.entity.ProductInCart;

@Service
public class OrderService {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private JdbcService jdbcService;

	public Order createOrder(HttpServletRequest request) {
		if (!cookieService.getCookie(request, "cart").getValue().equals("")) {
			List<ProductInCart> productList = productService.getCartProductList(cookieService.getCookie(request, "cart").getValue());
			Order order = new Order();
			for (ProductInCart prod: productList) {
				order.getProducts().add(prod);
			}
			return order;
		}
		return null;
	}
	
	public void saveOrderInDatabase(Order order, Integer customerId) {
		order.setCustomerId(customerId);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcService.addOrder(order, keyHolder);
		Integer orderId = keyHolder.getKey().intValue();
		order.setId(orderId);
		for (ProductInCart prod: order.getProducts()) {
			prod.setOrderId(orderId);
			jdbcService.addOrderlines(prod);
		}
	}

	public void updateOrderPayment(Integer id, boolean isPaid) {
		jdbcService.updateOrderPayment(id, isPaid);
	}
	
}
