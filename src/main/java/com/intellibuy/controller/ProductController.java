package com.intellibuy.controller;

import java.io.IOException;

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
import com.intellibuy.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value= {"product/list", "product/"})
	public ModelAndView productListView() {
		ModelAndView view = new ModelAndView("product/list");
		view.addObject("products", productService.findAll());
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
			HttpServletResponse response) throws IOException {
		if (productInCart.getNumber() <= 0) { return new ModelAndView("redirect:/product/detail/"+productInCart.getProductId()); }
		productService.addProductInCart(request, response, productInCart );
		ModelAndView view = new ModelAndView("product/add");
		view.addObject("product", productInCart);
		return view;
	}
	
	@RequestMapping("product/cart")
	public ModelAndView cartView(
			HttpServletRequest request,
			@CookieValue(value="cart", defaultValue="") String cartCookieValue ) throws IOException {
		ModelAndView view = new ModelAndView("product/cart");
		view.addObject("products", productService.getCartProductList( cartCookieValue));
		return view;
	}
	
	@RequestMapping(value="product/delete/{id}", method=RequestMethod.POST)
	public ModelAndView delete(
			HttpServletRequest request, 
			HttpServletResponse response,
			@PathVariable("id") String productId) throws NumberFormatException, IOException {
		productService.deleteProductInCart(request, response, Integer.parseInt(productId));
		return new ModelAndView("redirect:/product/cart");
	}
	
	@RequestMapping("product/order")
	public ModelAndView orderView(HttpServletRequest request) throws IOException {
		Order order = productService.createOrder(request);
		ModelAndView view = new ModelAndView("product/order");
		view.addObject("order", order);
		return view;
	}
	
	@RequestMapping("product/pay")
	public ModelAndView payView(HttpServletRequest request, HttpServletResponse response) {
		productService.deleteCartCookie(request,response);
		ModelAndView view = new ModelAndView("product/pay");
		return view;
	}

}
