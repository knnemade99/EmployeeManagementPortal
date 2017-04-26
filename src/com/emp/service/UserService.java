package com.emp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

import com.emp.entity.AuthTable;
import com.emp.entity.Operations;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.exceptions.ManualException;
import com.emp.pojos.ChangePasswordPOJO;
import com.emp.pojos.UserCredentialPOJO;

public interface UserService {
	
	public AuthTable login(UserCredentialPOJO logincredentials) throws ManualException;
	public ResponseEntity<String> logout(String authToken) throws DataAccessException , ManualException;
	public ResponseEntity<String> changePassword(String authToken, ChangePasswordPOJO passwords) throws DataAccessException, ManualException;
	public ResponseEntity<String> forgetPassword(String email) throws DataAccessException, ManualException;
	public ResponseEntity<String> updateProfile(User user, String authToken) throws DataAccessException, ManualException;
	public User viewProfile(String authToken) throws DataAccessException, ManualException;
	public ArrayList<Operations> viewHistory(String authToken) throws DataAccessException, ManualException;
	public List<Skill> viewAllSkills(String authToken) throws ManualException;
	
}
