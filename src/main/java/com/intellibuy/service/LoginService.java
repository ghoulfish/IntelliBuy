package com.intellibuy.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intellibuy.entity.Customer;
import com.intellibuy.entity.CustomerLoginInfo;

@Service
public class LoginService {
	
	@Autowired
	private JsonService jsonService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private JdbcService jdbcService;
	
	private List<Customer> _findAll() {
		return jdbcService.findAllUser();
	}
	
	private Customer _findByUsername(String username) {
		return jdbcService.findByUsername(username);
	}
	
	public List<Customer> findAll() {
		return _findAll();
	}
	
	public Customer findByUsername(String username) {
		return _findByUsername(username);
	}
	
	public boolean checkLogin(String username, String password) {
		Customer user;
		if ((user = findByUsername(username)) != null) {
			return password.equals(user.getPassword());
		} else {
			return false;
		}
	}
	
	public boolean checkLogin(String cookieValue) {
		if (cookieValue.equals("")) {
			return false;
		}
		CustomerLoginInfo loginInfo = jsonService.cookieValueToObject(cookieValue, CustomerLoginInfo.class);
		return checkLogin(loginInfo.getUsername(), loginInfo.getPassword());
	}


	public boolean isRegistered(String username) {
		jdbcService.checkTableValidation(new Customer());
		return ( findByUsername(username) != null ) ;
	}
	
	public void createNewUser(Customer customer) {
//		jdbcService.checkTableValidation(customer);
		if (jdbcService.find(customer, "username", customer.getUsername()) != null) {
			jdbcService.addToDatabase(customer);			
		} else {
			System.out.println("Username exist, check the input.");
		}
	}

	public void saveLoginCookie(HttpServletRequest request, HttpServletResponse response, String username) {
		Customer cust = findByUsername(username);
		CustomerLoginInfo custLogin = new CustomerLoginInfo();
		custLogin.setUsername(username);
		custLogin.setPassword(cust.getPassword());
		custLogin.setRole(cust.getRole());
		String cookieValue;
		try {
			cookieValue = jsonService.objectToCookieValue(custLogin);
			cookieService.createCookie(response, "login", cookieValue, "/", 60 * 60 * 24 * 7);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//System.out.println(custLogin);
	}

	public boolean hasValidLogin(HttpServletRequest request) {
		return (request != null)
				&&(cookieService.getCookieMap(request) != null) 
				&& (cookieService.getCookieMap(request).get("login") != null ) 
				&& ( checkLogin(cookieService.getCookieMap(request).get("login").getValue()) );
	}
	
	public String getRole(HttpServletRequest request) {
		if ( hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonService.cookieValueToObject(loginCookie.getValue(), CustomerLoginInfo.class);
			return custInfo.getRole();
		} else {			
			return "GUEST";
		}
	}

	public String getName(HttpServletRequest request) {
		if ( hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonService.cookieValueToObject(loginCookie.getValue(), CustomerLoginInfo.class);
			return custInfo.getUsername();
		} else {			
			return "GUEST";
		}
	}
	
	public Integer getCustomerId(HttpServletRequest request) {
		Cookie userCookie;
		Integer customerId = 0;
		if ((userCookie = cookieService.getCookie(request, "login")) != null && userCookie.getValue() != "") {
			CustomerLoginInfo user = jsonService.cookieValueToObject(userCookie.getValue(), CustomerLoginInfo.class);
			String username = user.getUsername();
			Customer cust = jdbcService.findByUsername(username);
			customerId = cust.getId();
		}
		return customerId;
	}
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		cookieService.delete(response, cookieService.getCookie(request, "login"));
	}
}
