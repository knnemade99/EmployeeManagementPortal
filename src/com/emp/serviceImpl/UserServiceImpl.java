package com.emp.serviceImpl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.UserDao;
import com.emp.entity.AuthTable;
import com.emp.entity.Operations;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/* User Login */
	@Override
	@Transactional
	public AuthTable login(UserCredential loginredentials) {
		if(loginredentials.validateUserCredential())
			return userDao.login(loginredentials);
		else
			return null;		
	}

	/* User Logout */
	@Override
	@Transactional
	public ResponseEntity<String> logout(String authToken) {
		return userDao.logout(authToken);
	}
	
	/* Change Password */
	@Override
	@Transactional
	public ResponseEntity<String> changePassword(String authToken, Map<String,String> passwords) {
		return userDao.changePassword(authToken,passwords);
	}

	/* Forget Password */
	@Override
	@Transactional
	public ResponseEntity<String> forgetPassword(Map<String, String> email) {
		return userDao.forgetPassword(email);
	}

	/* Update Profile */
	@Override
	@Transactional
	public ResponseEntity<String> updateProfile(User user, String authToken) {
		return userDao.updateProfile(user , authToken);
	}

	@Override
	@Transactional
	public User viewProfile(String authToken) {
		return userDao.viewProfile(authToken);
	}
	
	@Override
	@Transactional
	public ArrayList<Operations> viewHistory(String authToken) {
		return userDao.viewHistory(authToken);
	}
}
