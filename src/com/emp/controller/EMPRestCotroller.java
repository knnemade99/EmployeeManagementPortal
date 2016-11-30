package com.emp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;
import com.emp.service.AdminService;

@Controller
public class EMPRestCotroller {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/addemployee" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> adminLogin(@RequestBody UserCredentials usercredentials, @RequestHeader("authToken") String authToken){
		return adminService.addEmployee(usercredentials, authToken);
	}
	
	@RequestMapping(value="/login" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthTable login(@RequestBody Map<String,String> logincredentials){
		return adminService.login(logincredentials);
	}
	
	@RequestMapping(value="/logout" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> logout(@RequestHeader("authToken") String authToken){
		return adminService.logout(authToken);
	}
	
	@RequestMapping(value="/viewallemployees" , method = RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserCredentials> viewAllEmployees(@RequestHeader("authToken") String authToken){
		return adminService.viewAllEmployees(authToken);
	}

	@RequestMapping(value={"","/"} , method = RequestMethod.GET)
	public String shellPage(){
		return "shell";
	}
}
