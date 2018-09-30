package com.intellibuy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intellibuy.entity.Customer;
import com.intellibuy.entity.data.CustomerData;

@Service
public class LoginService {
	
	public boolean checkLogin(String username, String password) {
		Customer user;
		if ((user = CustomerData.getInstance().findByUsername(username)) != null) {
			return password.equals(user.getPassword());
		} else {
			return false;
		}
	}

	public List<Customer> findAll() {
		return CustomerData.getInstance().findAll();
	}

}
