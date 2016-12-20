package com.emp.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;

public interface AdminDao {
	
	public ResponseEntity<User> addEmployee(User user, String authToken);
	public List<UserCredential> viewAllEmployees(String authToken);
	public User viewEmployee(int employeeId, String authToken);
	public ResponseEntity<String> deleteEmployee(int employeeId, String authToken);
	public ResponseEntity<String> unlockEmployee(int employeeId, String authToken);
	public List<Department> viewAllDepartments(String authToken);
	public List<Project> viewAllProjects(String authToken);
	public List<Skill> viewAllSkills(String authToken);
	public ResponseEntity<String> addSkill(String authToken , Skill skill);
	public ResponseEntity<String> addDepartment(String authToken , Department newDepartment);
	public ResponseEntity<String> addProject(String authToken, Project project);
	public ResponseEntity<String> deleteProject(String authToken, int projectId);


}
