package com.intellibuy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.intellibuy.authority.AuthRole;
import com.intellibuy.entity.Customer;
import com.intellibuy.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@AuthRole(role="GUEST")
	@RequestMapping(value="login/", method={RequestMethod.GET})
	public ModelAndView loginView() {
		
		ModelAndView view = new ModelAndView("login/login");
		return view;
	}
	
	@RequestMapping(value="login/check", method=RequestMethod.POST)
	public ModelAndView loginCheck(
			@RequestParam("username") String username, 
			@RequestParam("password") String password,
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes reAttr) {
		if (loginService.checkLogin(username, password)) {
			loginService.saveLoginCookie(request, response, username);
			return new ModelAndView("redirect:/login/welcome");
		} else {
			reAttr.addFlashAttribute("err_msg", "Username or password is valid, please try again!");
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value="login/welcome")
	public ModelAndView loginWelcomeView() {
		ModelAndView view = new ModelAndView("login/welcome");
		return view;
	}
	
	@RequestMapping("login/list")
	public ModelAndView loginListView() {
		ModelAndView view = new ModelAndView("login/list");
		view.addObject("customers", loginService.findAll());
		return view;
	}
	
	@RequestMapping("login/register")
	public ModelAndView loginRegister() {
		ModelAndView view = new ModelAndView("login/register");
		return view;
	}
	
	@RequestMapping(value="login/register/check", method=RequestMethod.POST)
	public ModelAndView registerCheck(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute Customer customer, 
			RedirectAttributes reAttr) {
		if (loginService.isRegistered(customer.getUsername())) {
			reAttr.addFlashAttribute("err_msg", "Username: " + customer.getUsername() + " is used.");
			return new ModelAndView("redirect:/login/register/");
		} else {
			Customer cust = new Customer(customer);
			loginService.createNewUser(cust);
			loginService.saveLoginCookie(request, response, customer.getUsername());
			return new ModelAndView("redirect:/login/welcome");
		}
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		loginService.logout(request, response);
		return new ModelAndView("redirect:/index");
	}

}
