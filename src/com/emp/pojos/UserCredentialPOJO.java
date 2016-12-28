package com.emp.pojos;

public class UserCredentialPOJO {

	private String username;
	private String password;
	
	
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
	@Override
	public String toString() {
		return "UserCredential [username=" + username + ", password=" + password + "]";
	}
		
	public boolean validateUserCredential(){
		if(this.getUsername()!=null&&this.getUsername()!=""&&this.getPassword()!=null&&this.getPassword()!="")
			return true;
		else
			return false;
	}
}

