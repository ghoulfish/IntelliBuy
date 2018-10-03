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
	private JsonParser jsonParser;
	@Autowired
	private CookieService cookieService;
	
	public boolean checkLogin(String username, String password) {
		Customer user;
		if ((user = findByUsername(username)) != null) {
			return password.equals(user.getPassword());
		} else {
			return false;
		}
	}
	
	public boolean checkLogin(Cookie loginCookie) throws IOException {
		if (loginCookie.getValue().equals("")) {
			return false;
		}
		CustomerLoginInfo loginInfo = jsonParser.getCustomerLoginInfo(loginCookie);
		return checkLogin(loginInfo.getUsername(), loginInfo.getPassword());
	}

	public List<Customer> findAll() {
		return CustomerData.getInstance().findAll();
	}
	
	public Customer findByUsername(String username) {
		return CustomerData.getInstance().findByUsername(username);
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
		Cookie loginCookie = jsonParser.getCustomerLoginCookie(custLogin);
		cookieService.createCookie(response, loginCookie, "/", 60 * 60 * 24 * 7);
		
		//System.out.println(custLogin);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		cookieService.delete(response, cookieService.getCookie(request, "login"));
	}

}
