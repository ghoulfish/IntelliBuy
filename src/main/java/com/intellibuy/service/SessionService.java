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
	private AuthService authService;

	public void updateUserRole(HttpServletRequest request) throws IOException {
		request.getSession().setAttribute("role", authService.getRole(request));
		request.getSession().setAttribute("name", authService.getName(request));
	}

}
