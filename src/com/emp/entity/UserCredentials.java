package com.emp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="usercredentials")
public class UserCredentials implements Serializable {

	@Id
	@OneToOne
	@JoinColumn(name="employeeId")
	private User user;
	
	@Column(name="username",unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="usertype")
	private String usertype;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	@Override
	public String toString() {
		return "UserCredentials [user=" + user + ", username=" + username + ", password=" + password + ", usertype="
				+ usertype + "]";
	}
	
	
}

