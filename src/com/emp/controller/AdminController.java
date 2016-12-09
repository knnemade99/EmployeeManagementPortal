package com.emp.controller;

import java.util.List;
import java.util.Map;

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

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.service.AdminService;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	/* Adds new Employee */
	@RequestMapping(value="/addemployee" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> addemployee(@RequestBody User user, @RequestHeader("authToken") String authToken){
		return adminService.addEmployee(user, authToken);
	}
	
	/* View All Employees */
	@RequestMapping(value="/viewallemployees" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserCredential> viewAllEmployees(@RequestHeader("authToken") String authToken){
		return adminService.viewAllEmployees(authToken);
	}

	/* View Employee by Id */
	@RequestMapping(value="/viewemployee/{id}" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User viewEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken){
		return adminService.viewEmployee(employeeId,authToken);
	}
	
	/* Delete Employee by Id */
	@RequestMapping(value="/deleteemployee/{id}" , method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken){
		return adminService.deleteEmployee(employeeId,authToken);
	}
	
	/* Unlock Employee by Id */
	@RequestMapping(value="/unlockemployee/{id}" , method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> unlockEmployee(@PathVariable("id") int employeeId ,@RequestHeader("authToken") String authToken){
		return adminService.unlockEmployee(employeeId,authToken);
	}
}
