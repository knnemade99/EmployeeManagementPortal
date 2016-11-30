package com.emp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;

public interface AdminDao {
	
	public ResponseEntity<User> addEmployee(UserCredentials usercredentials, String authToken);
	public AuthTable login(Map<String,String> logincredentials);
	public ResponseEntity<String> logout(String authToken);
	public List<UserCredentials> viewAllEmployees(String authToken);
}
