package com.emp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;

public interface IndexService {
	
	public ResponseEntity<String> lockEmployee(String username);
}
