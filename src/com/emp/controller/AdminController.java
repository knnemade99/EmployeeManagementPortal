package com.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.exceptions.ManualException;
import com.emp.pojos.UserPOJO;
import com.emp.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	/* Adds new Employee */
	@RequestMapping(value="/addemployee" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> addemployee(@RequestBody UserPOJO user, @RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.addEmployee(user, authToken);
	}
	
	/* View All Employees */
	@RequestMapping(value="/viewallemployees" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserCredential> viewAllEmployees(@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.viewAllEmployees(authToken);
	}

	/* View Employee by Id */
	@RequestMapping(value="/viewemployee/{id}" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User viewEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.viewEmployee(employeeId,authToken);
	}
	
	/* Delete Employee by Id */
	@RequestMapping(value="/deleteemployee/{id}" , method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.deleteEmployee(employeeId,authToken);
	}
	
	/* Unlock Employee by Id */
	@RequestMapping(value="/unlockemployee/{id}" , method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> unlockEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.unlockEmployee(employeeId,authToken);
	}
	
	/* View All Departments */
	@RequestMapping(value="/viewalldepartments" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Department> viewAllDepartments(@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.viewAllDepartments(authToken);
	}
	
	/* View All Projects */
	@RequestMapping(value="/viewallprojects" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Project> viewAllProjects(@RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.viewAllProjects(authToken);
	}
	
	/* Add new Skill */
	@RequestMapping(value="/addskill" , method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addSkill(@RequestHeader("authToken") String authToken , @RequestBody Skill skill) throws ManualException{
		return adminService.addSkill(authToken , skill);
	}
	
	/* Add new Department */
	@RequestMapping(value="/adddepartment" , method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addDepartment(@RequestHeader("authToken") String authToken , @RequestBody Department newDepartment ) throws ManualException{
		return adminService.addDepartment(authToken , newDepartment);
	}
	
	/* Add new Project */
	@RequestMapping(value="/addproject" , method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addProject(@RequestHeader("authToken") String authToken , @RequestBody Project project ) throws ManualException{
		return adminService.addProject(authToken , project);
	}
	
	/* Delete a Project */
	@RequestMapping(value="/deleteproject/{id}" , method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteProject(@PathVariable("id") int projectId , @RequestHeader("authToken") String authToken) throws ManualException{
		return adminService.deleteProject(authToken , projectId);
	}
}
