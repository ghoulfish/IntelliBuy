package com.intellibuy.entity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intellibuy.entity.Customer;

public class CustomerData {
	
	private Map<String, Customer> CustomerByUsername;
	
	private static final CustomerData INSTANCE = new CustomerData();
	public static CustomerData getInstance() {
		return INSTANCE;
	}
	
	public CustomerData() {
		super();
		CustomerByUsername = new HashMap<>();
		CustomerByUsername.put("xixi", new Customer(1, "Xixi", "xixi", "123456"));
		CustomerByUsername.put("dongdong", new Customer(2, "Dongdong", "dongdong", "123456"));
		CustomerByUsername.put("user", new Customer(3, "User", "user", "123456"));
		CustomerByUsername.put("admin", new Customer(4, "Admin", "admin", "123456"));
	}
	
	public List<Customer> findAll(){
		return new ArrayList(INSTANCE.CustomerByUsername.values());
	}
	
	public Customer findByUsername(String username) {
		return this.CustomerByUsername.get(username);
	}

}
