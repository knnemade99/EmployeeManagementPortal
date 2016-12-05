package com.emp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.AuthTable;
import com.emp.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	/* User Login */
	@RequestMapping(value="/login" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthTable login(@RequestBody Map<String,String> logincredentials){
		return userService.login(logincredentials);
	}
	
	/* User Logout */
	@RequestMapping(value="/logout" , method = RequestMethod.DELETE,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> logout(@RequestHeader("authToken") String authToken){
		return userService.logout(authToken);
	}
	
	/* Changes Password */
	@RequestMapping(value="/changepassword" , method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> changePassword(@RequestBody Map<String,String> passwords,@RequestHeader("authToken") String authToken){
		return userService.changePassword(authToken, passwords);
	}
	
	/* Forget Password */
	@RequestMapping(value="/forgetpassword" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> forgetPassword(@RequestBody Map<String,String> email){
		return userService.forgetPassword(email);
	}
}
