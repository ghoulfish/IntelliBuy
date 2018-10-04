package com.intellibuy.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
	
//	@Autowired
//	private CookieService cookieService;
//	@Autowired
//	private LoginService loginService;
	@Autowired
	private LoginService loginService;

	public void updateUserRole(HttpServletRequest request) throws IOException {
		request.getSession().setAttribute("role", loginService.getRole(request));
		request.getSession().setAttribute("name", loginService.getName(request));
	}

}
