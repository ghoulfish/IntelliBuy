package com.intellibuy.service;

public class AuthDataSource extends AbstractAuthDataSource{

	@Override
	public String yourUrl() {
		return "jdbc:mysql://localhost:3306/IntelliBuy?useSSL=false&serverTimezone=America/New_York";
	}

	@Override
	public String yourUsername() {
		return "root";
	}

	@Override
	public String yourPassword() {
		return "ZCXzcx123";
	}

	@Override
	public String yourDriverClassName() {
		return "com.mysql.cj.jdbc.Driver";
	}

}
