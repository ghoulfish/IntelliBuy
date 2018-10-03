package com.intellibuy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.intellibuy.service.IndexService;
import com.intellibuy.service.SessionService;

@Controller
public class IndexController {
	
	@Autowired
	private IndexService indexService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value={"/", "/index"})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		indexService.loginByCookie(request, response);
		sessionService.updateUserRole(request);
		return new ModelAndView("welcome");
	}

}
