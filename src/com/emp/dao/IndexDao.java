package com.emp.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;

public interface IndexDao {

	ResponseEntity<String> unlockEmployee(String username);
	
}
