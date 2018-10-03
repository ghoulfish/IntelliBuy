package com.intellibuy.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.intellibuy.authority.AuthRole;
import com.intellibuy.service.AuthService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	public static final Set<String> USERROLES = new HashSet<>();
	static {
		USERROLES.add("USER");
		USERROLES.add("ADMIN");
	}
	
	@Autowired
	private AuthService authService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			AuthRole authRole = ((HandlerMethod) handler).getMethodAnnotation(AuthRole.class);
			if (authRole == null) { return true; }
			Set<String> permitRoles = new HashSet<>();
			addPermitRole(authRole.role(), permitRoles);
			if ( permitRoles.contains(authService.getRole(request)) ) {
				return true;
			} else {
				if (authService.getRole(request).equals("GUEST")) {
					response.sendRedirect("login");					
				} else {
					response.sendRedirect("./");	
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
