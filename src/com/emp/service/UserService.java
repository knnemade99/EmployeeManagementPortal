package com.emp.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.Operations;
import com.emp.entity.User;

public interface UserService {
	
	public AuthTable login(Map<String,String> logincredentials);
	public ResponseEntity<String> logout(String authToken);
	public ResponseEntity<String> changePassword(String authToken, Map<String, String> passwords);
	public ResponseEntity<String> forgetPassword(Map<String, String> email);
	public ResponseEntity<String> updateProfile(User user, String authToken);
	public User viewProfile(String authToken);
	public ArrayList<Operations> viewHistory(String authToken);
	
}
