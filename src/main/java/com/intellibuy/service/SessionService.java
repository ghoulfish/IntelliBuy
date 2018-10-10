package com.intellibuy.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
	
	@Autowired
	private LoginService loginService;

	public void updateUserRole(HttpServletRequest request) {
		request.getSession().setAttribute("role", loginService.getRole(request));
		request.getSession().setAttribute("name", loginService.getName(request));
	}

}
