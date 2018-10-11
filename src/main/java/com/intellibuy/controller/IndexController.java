package com.intellibuy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intellibuy.service.IndexService;
import com.intellibuy.service.SessionService;

@Controller
public class IndexController {
	
	private final boolean isInit = false;
	@Autowired
	private IndexService indexService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value={"/", "/index"})
	public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("welcome");
		if (isInit) {
			view.addObject("isInit", isInit);
			return view;
		}
		indexService.loginByCookie(request, response);
		sessionService.updateUserRole(request);
		return view;
	}

	@RequestMapping(value="/init", method=RequestMethod.POST)
	public ModelAndView init() {
		indexService.init();
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/destroy", method=RequestMethod.POST)
	public ModelAndView destroy() {
		indexService.destroy();
		return new ModelAndView("redirect:/");
	}

}
