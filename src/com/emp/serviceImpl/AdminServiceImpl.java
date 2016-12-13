package com.emp.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.emp.dao.AdminDao;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	/* Adds new Employee */
	@Override
	public ResponseEntity<User> addEmployee(User user, String authToken) {
		
		return adminDao.addEmployee(user, authToken);
	}

	/* View All Employees */
	@Override
	public List<UserCredential> viewAllEmployees(String authToken) {
		return adminDao.viewAllEmployees(authToken);
	}
	
	/* View Employee by Id */
	@Override
	public User viewEmployee(int employeeId,String authToken) {
		return adminDao.viewEmployee(employeeId,authToken);
	}
	
	/* Delete Employee by Id */
	@Override
	public ResponseEntity<String> deleteEmployee(int employeeId,String authToken) {
		return adminDao.deleteEmployee(employeeId,authToken);
	}
	
	/* Unlock Employee by Id */
	@Override
	public ResponseEntity<String> unlockEmployee(int employeeId,String authToken) {
		return adminDao.unlockEmployee(employeeId,authToken);
	}

	@Override
	public List<Department> viewAllDepartments(String authToken) {
		return adminDao.viewAllDepartments(authToken);
	}
	
	@Override
	public List<Project> viewAllProjects(String authToken) {
		return adminDao.viewAllProjects(authToken);
	}
	
	@Override
	public List<Skill> viewAllSkills(String authToken) {
		return adminDao.viewAllSkills(authToken);
	}
}
