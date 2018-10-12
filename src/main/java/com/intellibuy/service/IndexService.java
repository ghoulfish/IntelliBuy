package com.intellibuy.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellibuy.authority.BCrypt;
import com.intellibuy.entity.Customer;

@Service
public class IndexService {
	
	@Autowired
	private CookieService cookieService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private JdbcService jdbcService;

	public void loginByCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie loginCookie;
		if ( loginService.hasValidLogin(request) ) {
			loginCookie = cookieService.getCookie(request, "login");
			cookieService.renewCookie(response, loginCookie, 60 * 60 * 24 * 7);
		} else {
			cookieService.createCookie(response, "login", "", "/", -1);
		}
	}

	public void init() {
		jdbcService.init();
		String salt = BCrypt.gensalt(10);
		String hash = BCrypt.hashpw("123456", salt);
		Customer cust = new Customer("admin", hash, "ADMIN");
		Customer customer = new Customer(cust);
		loginService.createNewUser(customer);
	}

	public void destroy() {
		jdbcService.destroy();
	}

}
