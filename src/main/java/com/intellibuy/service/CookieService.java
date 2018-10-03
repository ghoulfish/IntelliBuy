package com.intellibuy.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
	
	@Autowired
	private LoginService loginService;

	public  Map<String, Cookie> getCookieMap (HttpServletRequest request){
		Map<String, Cookie> cookieMap = new HashMap<>();
		for (Cookie cookie: request.getCookies()) {
			cookieMap.put(cookie.getName(),	cookie);
		}
		return cookieMap;
	}
	
	public Cookie getCookie(HttpServletRequest request, String name) {
		return getCookieMap(request).get(name);
	}

	public void renewCookie(HttpServletResponse response, Cookie loginCookie, int expirySecond) {
		loginCookie.setMaxAge(expirySecond);
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
	}

	public void createCookie(HttpServletResponse response, String name, String value, String path, int expirySecond) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(expirySecond);
		response.addCookie(cookie);
	}

	public void createCookie(HttpServletResponse response, Cookie cookie, String path, int expirySecond) {
		String name = cookie.getName();
		String value = cookie.getValue();
		createCookie(response, name, value, path, expirySecond);
		
	}

	public boolean hasValidLogin(HttpServletRequest request) throws IOException {
		return ( getCookieMap(request).get("login") != null ) && ( loginService.checkLogin(getCookieMap(request).get("login")) );
	}

	public void delete(HttpServletResponse response, Cookie cookie) {
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}



}