package com.intellibuy.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IndexService {
	
	@Autowired
	private CookieService cookieService;

	public void loginByCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cookie loginCookie;
		if ( cookieService.hasValidLogin(request) ) {
			loginCookie = cookieService.getCookie(request, "login");
			cookieService.renewCookie(response, loginCookie, 60 * 60 * 24 * 7);
		} else {
			cookieService.createCookie(response, "login", "", "/", -1);
		}
	}

}
