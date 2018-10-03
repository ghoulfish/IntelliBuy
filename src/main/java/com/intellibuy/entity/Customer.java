package com.intellibuy.entity;

public class Customer {
	
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String username;
	private String password;
	
	public Customer() {
		super();
	}
	
	public Customer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Customer(Integer id, String name, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", username="
				+ username + ", password=" + password + "]";
	}

}
