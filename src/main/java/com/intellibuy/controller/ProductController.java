package com.intellibuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.intellibuy.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("product/list")
	public ModelAndView productListView() {
		ModelAndView view = new ModelAndView("product/list");
		view.addObject("products", productService.findAll());
		return view;
	}
	
	@RequestMapping("product/{id}/detail")
	public ModelAndView productView(@PathVariable("id") String id) {
		ModelAndView view = new ModelAndView("product/detail");
		view.addObject("product", productService.findById(Integer.parseInt(id)));
		return view;
	}

}
