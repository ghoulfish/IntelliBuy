package com.intellibuy.service;

public class AuthDataSource extends AbstractAuthDataSource{

	@Override
	public String yourUrl() {
		return "";
	}

	@Override
	public String yourUsername() {
		return "";
	}

	@Override
	public String yourPassword() {
		return "";
	}

	@Override
	public String yourDriverClassName() {
		return "com.mysql.cj.jdbc.Driver";
	}

}
