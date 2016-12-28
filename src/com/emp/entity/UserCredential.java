package com.emp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="usercredential")
public class UserCredential {

	@Id
	@Column(name="username")
	@NotEmpty(message="Username can't be empty")
	private String username;
	
	@Column(name="password")
	@NotEmpty(message="Password can't be empty")
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

}

