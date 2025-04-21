package com.test.admin;

public class Admin {
	private final String adminId;
	private final String password;
	
	
	public Admin(String adminId, String password) {
		this.adminId = adminId;
		this.password = password;
	}

	public String getAdminId() {
		return adminId;
	}
	
	public String getPassword() {
		return password;
	}
	
	
}
