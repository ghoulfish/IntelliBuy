package com.intellibuy.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.intellibuy.authority.AuthRole;

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
			AuthRole authRoleClass = ((HandlerMethod) handler).getBeanType().getAnnotation(AuthRole.class);
			AuthRole authRoleMethod = ((HandlerMethod) handler).getMethodAnnotation(AuthRole.class);
			AuthRole authRole = authRoleMethod == null? authRoleClass: authRoleMethod;
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
