package com.intellibuy.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.CustomerLoginInfo;

@Service
public class AuthService {
	
	@Autowired
	private CookieService cookieService;
	@Autowired
	private JsonParser jsonParser;

	public String getRole(HttpServletRequest request) throws IOException {
		if ( cookieService.hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonParser.getCustomerLoginInfo(loginCookie);
			return custInfo.getRole();
		} else {			
			return "GUEST";
		}
	}

	public String getName(HttpServletRequest request) throws IOException {
		if ( cookieService.hasValidLogin(request) ) {
			Cookie loginCookie = cookieService.getCookie(request, "login");
			CustomerLoginInfo custInfo = jsonParser.getCustomerLoginInfo(loginCookie);
			return custInfo.getUsername();
		} else {			
			return "GUEST";
		}
	}

}
