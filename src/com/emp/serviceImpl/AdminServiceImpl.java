package com.emp.serviceImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.AdminDao;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.exceptions.ManualException;
import com.emp.pojos.UserPOJO;
import com.emp.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private Environment env;
	
	/* Adds new Employee */
	@Override
	@Transactional
	public ResponseEntity<User> addEmployee(UserPOJO userPojo, String authToken) throws ManualException {
		if(userPojo.validateUser()){
			
			User user=new User();
			user.setAbout(userPojo.getAbout());
			user.setAddress(userPojo.getAddress());
			user.setContact(userPojo.getContact());
			user.setDepartment(userPojo.getDepartment());
			user.setDesignation(userPojo.getDesignation());
			user.setDob(userPojo.getDob());
			user.setDoj(userPojo.getDoj());
			user.setEmail(userPojo.getEmail());
			user.setExperience(userPojo.getExperience());
			user.setGender(userPojo.getGender());
			user.setManagerId(userPojo.getManagerId());
			user.setMaritalStatus(userPojo.getMaritalStatus());
			user.setName(userPojo.getName());
			user.setProject(userPojo.getProject());
			user.setSalary(userPojo.getSalary());
			user.setSkills(userPojo.getSkills());
			user.setUserCredential(userPojo.getUserCredential());
			user.setUsertype(userPojo.getUsertype());
			user.setLockStatus(userPojo.getLockStatus());
			
			return adminDao.addEmployee(user, authToken);
		}
		else
			throw new ManualException("user.entries.invalid",env.getProperty("user.entries.invalid"));
	}

	/* View All Employees */
	@Override
	@Transactional
	public List<UserCredential> viewAllEmployees(String authToken) throws ManualException {
		return adminDao.viewAllEmployees(authToken);
	}
	
	/* View Employee by Id */
	@Override
	@Transactional
	public User viewEmployee(int employeeId,String authToken) throws ManualException {
		if(employeeId!=0){
			User user= adminDao.viewEmployee(employeeId,authToken);
			if(user!=null){
				return user;
			}
			else{
				throw new ManualException("view.emp.notfound",env.getProperty("view.emp.notfound"));
			}
		}
		else{
			throw new ManualException("view.emp.by.id.error",env.getProperty("view.emp.by.id.error"));
		}
	}
	
	/* Delete Employee by Id */
	@Override
	@Transactional
	public ResponseEntity<String> deleteEmployee(int employeeId,String authToken) throws ManualException {
		
		if(employeeId!=0){
			ResponseEntity<String> responseEntity = adminDao.deleteEmployee(employeeId,authToken); 
			if(responseEntity!=null){
				return responseEntity;
			}
			else{
				throw new ManualException("delete.emp.notfound",env.getProperty("delete.emp.notfound"));
			}
		}
		else{
			throw new ManualException("delete.emp.by.id.error",env.getProperty("delete.emp.by.id.error"));
		}
	}
	
	/* Unlock Employee by Id */
	@Override
	@Transactional
	public ResponseEntity<String> unlockEmployee(int employeeId,String authToken) throws ManualException {
		if(employeeId!=0){
			ResponseEntity<String> responseEntity = adminDao.unlockEmployee(employeeId,authToken); 
			if(responseEntity!=null){
				return responseEntity;
			}
			else{
				throw new ManualException("delete.emp.notfound",env.getProperty("delete.emp.notfound"));
			}
		}
		else{
			throw new ManualException("delete.emp.by.id.error",env.getProperty("delete.emp.by.id.error"));
		}
	}

	@Override
	@Transactional
	public List<Department> viewAllDepartments(String authToken) throws ManualException{
		List<Department> departments = adminDao.viewAllDepartments(authToken);
		if(!departments.isEmpty()){
			return departments;
		}
		else{
			throw new ManualException("department.list.empty",env.getProperty("department.list.empty"));
		}
	}
	
	@Override
	@Transactional
	public List<Project> viewAllProjects(String authToken) throws ManualException{
		List<Project> projectList= adminDao.viewAllProjects(authToken);
		if(!projectList.isEmpty()){
			return projectList;
		}
		else{
			throw new ManualException("project.list.empty",env.getProperty("project.list.empty"));
		}
	}
	
	@Override
	@Transactional
	public List<Skill> viewAllSkills(String authToken) throws ManualException{
		List<Skill> skillList= adminDao.viewAllSkills(authToken);
		if(!skillList.isEmpty()){
			return skillList;
		}
		else{
			throw new ManualException("skill.list.empty",env.getProperty("skill.list.empty"));
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> addSkill(String authToken, Skill skill) throws ManualException{
		if(skill!=null){
			ResponseEntity<String> responseEntity = adminDao.addSkill(authToken,skill);
			if(responseEntity!=null)
				return responseEntity;
			else
				throw new ManualException("skill.not.added",env.getProperty("skill.not.added"));
		}
		else{
			throw new ManualException("skill.add.empty",env.getProperty("skill.add.empty"));
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> addDepartment(String authToken, Department newDepartment) throws ManualException{
		if(newDepartment!=null){
			ResponseEntity<String> responseEntity =  adminDao.addDepartment(authToken,newDepartment);
			if(responseEntity!=null)
				return responseEntity;
			else
				throw new ManualException("department.not.added",env.getProperty("department.not.added"));
		}
		else{
			throw new ManualException("department.add.empty",env.getProperty("department.add.empty"));
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> addProject(String authToken, Project project) throws ManualException{
		if(project!=null){
			ResponseEntity<String> responseEntity = adminDao.addProject(authToken,project);
			if(responseEntity!=null)
				return responseEntity;
			else
				throw new ManualException("project.not.added",env.getProperty("project.not.added"));
		}
		else{
			throw new ManualException("project.add.empty",env.getProperty("project.add.empty"));
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<String> deleteProject(String authToken, int projectId) throws ManualException{
		if(projectId!=0){
			ResponseEntity<String> responseEntity = adminDao.deleteProject(authToken,projectId);
			if(responseEntity!=null)
				return responseEntity;
			else
				throw new ManualException("project.not.deleted",env.getProperty("project.not.deleted"));
		}
		else{
			throw new ManualException("delete.projectId.required",env.getProperty("delete.projectId.required"));
		}
	}
}
