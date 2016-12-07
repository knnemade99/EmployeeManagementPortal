package com.emp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredential;

public interface AdminDao {
	
	public ResponseEntity<User> addEmployee(User user, String authToken);
	public List<UserCredential> viewAllEmployees(String authToken);
	public User viewEmployee(int employeeId, String authToken);
	public ResponseEntity<String> deleteEmployee(int employeeId, String authToken);
	public ResponseEntity<String> unlockEmployee(int employeeId, String authToken);

}
