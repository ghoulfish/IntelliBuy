package com.intellibuy.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.intellibuy.authority.AuthRole;
import com.intellibuy.service.LoginService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	public static final Set<String> USERROLES = new HashSet<>();
	static {
		USERROLES.add("USER");
		USERROLES.add("ADMIN");
	}
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			AuthRole authRole = ((HandlerMethod) handler).getMethodAnnotation(AuthRole.class);
			if (authRole == null) { return true; }
			Set<String> permitRoles = new HashSet<>();
			addPermitRole(authRole.role(), permitRoles);
			if ( permitRoles.contains(loginService.getRole(request)) ) {
				return true;
			} else {
				if (loginService.getRole(request).equals("GUEST")) {
					response.sendRedirect("/IntelliBuy/login");					
				} else {
					response.sendRedirect("/IntelliBuy");	
				}
				return false;
			}
		} else {
			return true;
		}
	}

	private void addPermitRole(String[] roles, Set<String> permitRoles) {
		for(String role: roles) {
			permitRoles.add(role);
		}
	}


}
