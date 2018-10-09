package com.intellibuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.intellibuy.authority.AuthRole;
import com.intellibuy.entity.Product;
import com.intellibuy.service.JdbcService;

@AuthRole(role= {"ADMIN"})
@Controller
public class AdminController {
	
	@Autowired
	private JdbcService jdbcService;
	
	@RequestMapping(value = {"/admin", "/admin/index"})
	public ModelAndView adminWelcomeView() {
		ModelAndView view = new ModelAndView("admin/welcome");
		return view;
	}
	
	@RequestMapping("/admin/add")
	public ModelAndView addProductView() {
		ModelAndView view = new ModelAndView("admin/add");
		return view;
	}
	
	@RequestMapping(value="/admin/add/check", method=RequestMethod.POST)
	public ModelAndView addProductCheck(
			@ModelAttribute Product product,
			BindingResult res,
			RedirectAttributes reAttr) {
		if (res.hasErrors()) {
			for (FieldError err: res.getFieldErrors()) {
				System.out.println(err.getField() +" : "+err.getDefaultMessage());
			}
		}
		if (jdbcService.findProductByName(product.getName()) != null) {
			product.setId(jdbcService.findProductByName(product.getName()).getId());
			reAttr.addFlashAttribute("err_msg", "Product exists");
			reAttr.addFlashAttribute("productNew", product);
			return new ModelAndView("redirect:/admin/modify/"+product.getId());
		} else {
			jdbcService.addProduct(product);
			reAttr.addFlashAttribute("msg", "Add product successfully");
			return new ModelAndView("redirect:/admin/add");
		}
	}
	
	@RequestMapping("/admin/show")
	public ModelAndView showProductView() {
		ModelAndView view = new ModelAndView("admin/show");
		view.addObject("products", jdbcService.findAll(new Product()));
		return view;
	}
	
	@RequestMapping("/admin/modify/{id:[0-9]+}")
	public ModelAndView modifyProductView(@PathVariable("id") String id) {
		ModelAndView view = new ModelAndView("admin/modify");
		Product product;
		if ( (product = jdbcService.findProductById(Integer.parseInt(id)) ) == null ){
			view.addObject("err_msg", "Product not found");
		} else {
			view.addObject("product", product);
		}
		return view;
	}
	
	@RequestMapping(value="/admin/modify/check", method=RequestMethod.POST)
	public ModelAndView modifyCheck(
			@ModelAttribute Product productNew,
			BindingResult res,
			RedirectAttributes reAttr) {
		if (res.hasErrors()) {
			for (FieldError err: res.getFieldErrors()) {
				System.out.println(err.getField() +" : "+err.getDefaultMessage());
			}
		}
		jdbcService.updateProduct(productNew);
		return new ModelAndView("redirect:/admin/");
	}	
	
	@RequestMapping("/admin/delete/{id:[0-9]+}")
	public ModelAndView deleteProductById(@PathVariable("id") String id, RedirectAttributes reAttr) {
		jdbcService.deleteProductById(new Integer(id));
		reAttr.addFlashAttribute("msg", "Delete product successfully.");
		return new ModelAndView("redirect:/admin/show");
	}
	
	@RequestMapping("/admin/orders/")
	public ModelAndView orderView() {
		ModelAndView view = new ModelAndView("admin/orders");
		view.addObject("orders", jdbcService.findAllOrder());
		return view;
	}
	
}
