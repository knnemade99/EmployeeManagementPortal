package com.emp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredential;

public interface AdminService {
	
	public ResponseEntity<User> addEmployee(User user, String authToken);
	public List<UserCredential> viewAllEmployees(String authToken);
	public User viewEmployee(int employeeId, String authToken);
	public ResponseEntity<String> deleteEmployee(int employeeId, String authToken);
	
}
