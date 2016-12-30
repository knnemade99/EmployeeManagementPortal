package com.emp.serviceImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.UserDao;
import com.emp.entity.AuthTable;
import com.emp.entity.Operations;
import com.emp.entity.User;
import com.emp.exceptions.ManualException;
import com.emp.pojos.ChangePasswordPOJO;
import com.emp.pojos.UserCredentialPOJO;
import com.emp.service.UserService;
import com.emp.util.Encrypt;

@PropertySource({
	"classpath:email.properties",
	"classpath:exceptions.properties"
})
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Environment env;
	
	/* User Login */
	@Override
	@Transactional
	public AuthTable login(UserCredentialPOJO logincredentials) throws ManualException {
		AuthTable authTable=null;
		if(logincredentials.validateUserCredential()){
			
			/* Encrypts Password */
			logincredentials.setPassword(Encrypt.encrypt(logincredentials.getPassword()));
			
			authTable = userDao.login(logincredentials);
			if(authTable==null)
				throw new ManualException("user.credential.invalid",env.getProperty("user.credential.invalid"));
		}
		else
			throw new ManualException("username.null",env.getProperty("username.null"));
		return authTable;

	}

	/* User Logout */
	@Override
	@Transactional
	public ResponseEntity<String> logout(String authToken) throws DataAccessException, ManualException {
		ResponseEntity<String> responseEntity = null;
		if(authToken!=null)
			responseEntity =  userDao.logout(authToken);
		else{
			throw new ManualException("authtoken.null",env.getProperty("authtoken.null"));
		}
		return responseEntity;
	}
	
	/* Change Password */
	@Override
	@Transactional
	public ResponseEntity<String> changePassword(String authToken, ChangePasswordPOJO passwords) throws DataAccessException, ManualException {
		ResponseEntity<String> responseEntity = null;
		if(passwords.validate()){
			passwords.setOldPassword(Encrypt.encrypt(passwords.getOldPassword()));
			passwords.setNewPassword(Encrypt.encrypt(passwords.getNewPassword()));
			responseEntity= userDao.changePassword(authToken,passwords);
		}
		else
			throw new ManualException("passwords.empty",env.getProperty("passwords.empty"));
		
		return responseEntity;
	}

	/* Forget Password */
	@Override
	@Transactional
	public ResponseEntity<String> forgetPassword(String email) throws DataAccessException, ManualException {
		ResponseEntity<String> responseEntity = null;
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);
		if(pattern.matcher(email).matches()){
			responseEntity =  userDao.forgetPassword(email);
		}
		else{
			throw new ManualException("email.invalid",env.getProperty("email.invalid"));
		}
		return responseEntity;
	}

	/* Update Profile */
	@Override
	@Transactional
	public ResponseEntity<String> updateProfile(User user, String authToken)  throws DataAccessException, ManualException{
		return userDao.updateProfile(user , authToken);
	}

	@Override
	@Transactional
	public User viewProfile(String authToken) throws DataAccessException, ManualException {
		User user=null;
		if(authToken!=null){
			user= userDao.viewProfile(authToken);
		}
		else
			throw new ManualException("authtoken.null",env.getProperty("authtoken.null"));
		return user;
	}
	
	@Override
	@Transactional
	public ArrayList<Operations> viewHistory(String authToken) throws DataAccessException, ManualException {
		ArrayList<Operations> operations=null;
		if(authToken!=null)
			operations= userDao.viewHistory(authToken);
		else
			throw new ManualException("authtoken.null",env.getProperty("authtoken.null"));
		return operations;
	}
}
