package com.emp.dao;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;

public interface UserDao {
	
	public AuthTable login(Map<String,String> logincredentials);
	public ResponseEntity<String> logout(String authToken);
	public ResponseEntity<String> changePassword(String authToken, Map<String, String> passwords);
	public ResponseEntity<String> forgetPassword(Map<String, String> email);
	
}
