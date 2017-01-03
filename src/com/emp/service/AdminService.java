package com.emp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.exceptions.ManualException;
import com.emp.pojos.UserPOJO;

public interface AdminService {
	
	public ResponseEntity<User> addEmployee(UserPOJO user, String authToken) throws ManualException;
	public List<UserCredential> viewAllEmployees(String authToken) throws ManualException;
	public User viewEmployee(int employeeId, String authToken) throws ManualException;
	public ResponseEntity<String> deleteEmployee(int employeeId, String authToken) throws ManualException;
	public ResponseEntity<String> unlockEmployee(int employeeId, String authToken) throws ManualException;
	public List<Department> viewAllDepartments(String authToken) throws ManualException;
	public List<Project> viewAllProjects(String authToken) throws ManualException;
	public List<Skill> viewAllSkills(String authToken) throws ManualException;
	public ResponseEntity<String> addSkill(String authToken , Skill skill) throws ManualException;
	public ResponseEntity<String> addDepartment(String authToken , Department newDepartment) throws ManualException;
	public ResponseEntity<String> addProject(String authToken, Project project) throws ManualException;
	public ResponseEntity<String> deleteProject(String authToken, int  projectId) throws ManualException;
	
}
