package com.intellibuy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.intellibuy.entity.Order;
import com.intellibuy.entity.ProductInCart;
import com.intellibuy.service.JdbcService;
import com.intellibuy.service.LoginService;
import com.intellibuy.service.OrderService;
import com.intellibuy.service.ProductService;
import com.intellibuy.service.SearchService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private JdbcService jdbcService;
	
	@RequestMapping(value= {"product/list", "product/"})
	public ModelAndView productListView() {
		ModelAndView view = new ModelAndView("product/list");
		view.addObject("products", productService.findAll().toArray());
		return view;
	}
	
	@RequestMapping("product/search")
	public ModelAndView productSearchView(@RequestParam("search_prod_name") String searchProductName) {
		ModelAndView view = new ModelAndView("product/list");
		view.addObject("products", jdbcService.searchProduct(searchProductName));
		return view;
	}
	
	@RequestMapping("product/detail/{id}")
	public ModelAndView productView(@PathVariable("id") String id) {
		ModelAndView view = new ModelAndView("product/detail");
		view.addObject("product", productService.findById(Integer.parseInt(id)));
		return view;
	}
	
	@RequestMapping("product/add")
	public ModelAndView addProductView(
			@ModelAttribute ProductInCart productInCart,
			HttpServletRequest request,
			HttpServletResponse response) {
		if (productInCart.getNumber() <= 0) { return new ModelAndView("redirect:/product/detail/"+productInCart.getProductId()); }
		productService.addProductInCart(request, response, productInCart );
		ModelAndView view = new ModelAndView("product/add");
		view.addObject("product", productInCart);
		return view;
	}
	
	@RequestMapping("product/cart")
	public ModelAndView cartView(
			HttpServletRequest request,
			@CookieValue(value="cart", defaultValue="") String cartCookieValue ) {
		ModelAndView view = new ModelAndView("product/cart");
		view.addObject("products", productService.getCartProductList( cartCookieValue));
		return view;
	}
	
	@RequestMapping(value="product/delete/{id}", method=RequestMethod.POST)
	public ModelAndView deleteProductInCart(
			HttpServletRequest request, 
			HttpServletResponse response,
			@PathVariable("id") String productId) throws NumberFormatException{
		productService.deleteProductInCart(request, response, Integer.parseInt(productId));
		return new ModelAndView("redirect:/product/cart");
	}
	
	@RequestMapping("product/order")
	public ModelAndView orderView(HttpServletRequest request, HttpServletResponse response) {
		Order order = orderService.createOrder(request);
		if (!jdbcService.hasEnoughStock(order)) {
			jdbcService.removeNoStock(request, response);
			return new ModelAndView("product/cart");
		}
		ModelAndView view = new ModelAndView("product/order");
		Integer customerId = loginService.getCustomerId(request);
		orderService.saveOrderInDatabase(order, customerId);
		productService.deleteCartCookie(request,response);
		view.addObject("order", order);
		return view;
	}
	
	@RequestMapping("product/pay/{orderId:[0-9]+}")
	public ModelAndView payView(@PathVariable("orderId") String orderId) {
		boolean isPaid = true;
		ModelAndView view = new ModelAndView("product/pay");
		if (isPaid) {
			orderService.updateOrderPayment(Integer.parseInt(orderId), isPaid);
			jdbcService.decreaseProductStock(Integer.parseInt(orderId));
		}
		return view;
	}

}
