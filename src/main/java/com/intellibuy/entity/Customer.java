package com.intellibuy.entity;

import java.time.LocalDate;

public class Customer extends EntityToDB{
	
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String username;
	private String password;
	private String role;
	private LocalDate userSince;
	private LocalDate notVisitSince;
	
	public Customer() {
		super();
	}
	
	public Customer(Customer cust) {
		super();
		this.username = cust.getUsername();
		this.password = cust.getPassword();
		this.email = cust.getEmail();
		this.role = cust.getRole();
		if (cust.getRole() != "ADMIN") {
			this.role = "USER";	
		}
		this.userSince = LocalDate.now();
		this.notVisitSince = LocalDate.now();
	}
	
	public Customer(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public Customer(String name, String username, String password, String role) {
		this(username, password, role);
		this.name = name;

	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getUserSince() {
		return userSince;
	}

	public void setUserSince(LocalDate userSince) {
		this.userSince = userSince;
	}

	public LocalDate getNotVisitSince() {
		return notVisitSince;
	}

	public void setNotVisitSince(LocalDate notVisitSince) {
		this.notVisitSince = notVisitSince;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", username="
				+ username + ", password=" + password + "]";
	}

}
