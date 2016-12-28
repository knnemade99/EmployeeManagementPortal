package com.emp.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.emp.entity.Operations;
import com.emp.entity.User;
import com.emp.exceptions.ManualException;
import com.emp.pojos.ChangePasswordPOJO;
import com.emp.pojos.UserCredentialPOJO;
import com.emp.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	/* User Login */
	@RequestMapping(value="/login" , method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthTable login(@RequestBody UserCredentialPOJO logincredentials) throws ManualException{
		return userService.login(logincredentials);
	}
	
	/* User Logout */
	@RequestMapping(value="/logout" , method = RequestMethod.DELETE,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> logout(@RequestHeader("authToken") String authToken) throws DataAccessException , ManualException{
		return userService.logout(authToken);
	}
	
	/* Changes Password */
	@RequestMapping(value="/changepassword" , method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordPOJO passwords,@RequestHeader("authToken") String authToken) throws DataAccessException, ManualException{
		return userService.changePassword(authToken, passwords);
	}
	
	/* Forget Password */
	@RequestMapping(value="/forgetpassword" , method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> forgetPassword(@RequestBody Map<String,String> userEmail)  throws DataAccessException, ManualException{
		return userService.forgetPassword(userEmail.get("email"));
	}
	
	/* Update Profile */
	@RequestMapping(value="/updateprofile" , method = RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> updateProfile(@RequestBody User user, @RequestHeader("authToken") String authToken) throws DataAccessException, ManualException{
		return userService.updateProfile(user , authToken);
	}
	
	/* View Profile */
	@RequestMapping(value="/viewprofile" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User viewProfile(@RequestHeader("authToken") String authToken) throws DataAccessException, ManualException{
		return userService.viewProfile(authToken);
	}
	
	/* View History */
	@RequestMapping(value="/viewhistory" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<Operations> viewHistory(@RequestHeader("authToken") String authToken) throws DataAccessException, ManualException{
		return userService.viewHistory(authToken);
	}
}
