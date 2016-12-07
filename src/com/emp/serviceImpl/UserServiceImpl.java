package com.emp.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.emp.dao.UserDao;
import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/* User Login */
	@Override
	public AuthTable login(Map<String,String> loginredentials) {
		return userDao.login(loginredentials);
	}

	/* User Logout */
	@Override
	public ResponseEntity<String> logout(String authToken) {
		return userDao.logout(authToken);
	}
	
	/* Change Password */
	@Override
	public ResponseEntity<String> changePassword(String authToken, Map<String,String> passwords) {
		return userDao.changePassword(authToken,passwords);
	}

	/* Forget Password */
	@Override
	public ResponseEntity<String> forgetPassword(Map<String, String> email) {
		return userDao.forgetPassword(email);
	}

	/* Update Profile */
	@Override
	public ResponseEntity<String> updateProfile(User user, String authToken) {
		return userDao.updateProfile(user , authToken);
	}

	@Override
	public User viewProfile(String authToken) {
		return userDao.viewProfile(authToken);
	}
}
