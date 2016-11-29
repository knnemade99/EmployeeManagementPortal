package com.emp.serviceImpl;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.emp.dao.AdminDao;
import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;
import com.emp.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDao admindao;
	
	@Override
	public ResponseEntity<User> addEmployee(UserCredentials usercredentials, String authToken) {
		
		return admindao.addEmployee(usercredentials, authToken);
	}

	@Override
	public AuthTable login(Map<String,String> loginredentials) {
		return admindao.login(loginredentials);
	}

	@Override
	public ResponseEntity<String> logout(String authToken) {
		return admindao.logout(authToken);
	}

}
