package com.intellibuy.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.Customer;
import com.intellibuy.entity.CustomerLoginInfo;
import com.intellibuy.entity.data.CustomerData;

@Service
public class LoginService {
	
	@Autowired
	private JsonService jsonService;
	@Autowired
	private CookieService cookieService;
	
	private List<Customer> _findAll() {
		return CustomerData.getInstance().findAll();
	}
	
	private Customer _findByUsername(String username) {
		return CustomerData.getInstance().findByUsername(username);
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
	
	public boolean checkLogin(String cookieValue) throws IOException {
		if (cookieValue.equals("")) {
			return false;
		}
		CustomerLoginInfo loginInfo = jsonService.cookieValueToObject(cookieValue, CustomerLoginInfo.class);
		return checkLogin(loginInfo.getUsername(), loginInfo.getPassword());
	}


	public boolean isRegistered(String username) {
		return ( findByUsername(username) != null ) ;
	}
	
	public void createNewUser(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	public void saveLoginCookie(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {
		Customer cust = findByUsername(username);
		CustomerLoginInfo custLogin = new CustomerLoginInfo();
		custLogin.setUsername(username);
		custLogin.setPassword(cust.getPassword());
		custLogin.setRole(cust.getRole());
		String cookieValue = jsonService.objectToCookieValue(custLogin);
		cookieService.createCookie(response, "login", cookieValue, "/", 60 * 60 * 24 * 7);
		
		//System.out.println(custLogin);
	}

	public boolean hasValidLogin(HttpServletRequest request) throws IOException {
		return (cookieService.getCookieMap(request).get("login") != null ) 
				&& ( checkLogin(cookieService.getCookieMap(request).get("login").getValue()) );
	}
	
	public String getRole(HttpServletRequest request) throws IOException {
		if ( hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonService.cookieValueToObject(loginCookie.getValue(), CustomerLoginInfo.class);
			return custInfo.getRole();
		} else {			
			return "GUEST";
		}
	}

	public String getName(HttpServletRequest request) throws IOException {
		if ( hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonService.cookieValueToObject(loginCookie.getValue(), CustomerLoginInfo.class);
			return custInfo.getUsername();
		} else {			
			return "GUEST";
		}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		cookieService.delete(response, cookieService.getCookie(request, "login"));
	}
}
