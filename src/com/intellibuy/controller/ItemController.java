package com.intellibuy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ItemController {

	@RequestMapping("/item/{item_name}")
	public ModelAndView showItem(@PathVariable("item_name") String name) {
		ModelAndView view = new ModelAndView("item-view");
		view.addObject("item_name", name);
		return view;
	}
	
}
