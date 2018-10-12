package com.intellibuy.controller;

import java.io.IOException;

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
import com.intellibuy.service.JdbcService;
import com.intellibuy.service.LoginService;

@Controller
public class LoginController {
	
	private final boolean saveUser = true;
	@Autowired
	private LoginService loginService;
	@Autowired
	private JdbcService jdbcService;

	@AuthRole(role="GUEST")
	@RequestMapping(value="login/", method={RequestMethod.GET})
	public ModelAndView loginView() {
		ModelAndView view = new ModelAndView("login/login");
		return view;
	}
	
	@RequestMapping(value="login/getpassword", method={RequestMethod.GET})
	public void getpassword(@RequestParam("username") String username, HttpServletResponse response) {
		String password = jdbcService.getPassword(username);
		System.out.println(username + ", " + password);
		try {
			response.getWriter().print(password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return new ModelAndView("login/login");
	}
	
	@RequestMapping(value="login/check", method=RequestMethod.POST)
	public ModelAndView loginCheck(
			@RequestParam("username") String username, 
			HttpServletRequest request,
			HttpServletResponse response) {
		loginService.saveLoginCookie(request, response, username);
		return new ModelAndView("redirect:/login/welcome");
	}
	
	@AuthRole(role={"USER", "ADMIN"})
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
	
	@AuthRole(role="GUEST")
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
		if (!saveUser) {
			System.out.println(customer);
			return new ModelAndView("redirect:/login/register");
		}
		if (loginService.isRegistered(customer.getUsername())) {
			reAttr.addFlashAttribute("err_msg", "Username: " + customer.getUsername() + " is used.");
			return new ModelAndView("redirect:/login/register");
		} else {
			Customer cust = new Customer(customer);
			loginService.createNewUser(cust);
			loginService.saveLoginCookie(request, response, customer.getUsername());
			return new ModelAndView("redirect:/login/welcome");
		}
	}
	
	@AuthRole(role= {"USER", "ADMIN"})
	@RequestMapping("login/orders")
	public ModelAndView yourOrders(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("login/orders");
		String username = loginService.getName(request);
		Integer customerId = jdbcService.findByUsername(username).getId();
		view.addObject("orders", jdbcService.findOrderByCustomerId(customerId));
		return view;
	}
	
	@AuthRole(role= {"USER", "ADMIN"})
	@RequestMapping("login/profile")
	public ModelAndView profileView() {
		ModelAndView view = new ModelAndView("lobin/profile");
		return view;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		loginService.logout(request, response);
		return new ModelAndView("redirect:/index");
	}

}
