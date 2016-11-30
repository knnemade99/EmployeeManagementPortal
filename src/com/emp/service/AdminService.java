package com.emp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;

public interface AdminService {
	public ResponseEntity<User> addEmployee(UserCredentials userCredentials, String authToken);
	public AuthTable login(Map<String,String> logincredentials);
	public ResponseEntity<String> logout(String authToken);
	public List<UserCredentials> viewAllEmployees(String authToken);
}
