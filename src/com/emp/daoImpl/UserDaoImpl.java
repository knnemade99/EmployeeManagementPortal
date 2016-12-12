package com.emp.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.UserDao;
import com.emp.email.EmailAPI;
import com.emp.entity.Address;
import com.emp.entity.AuthTable;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Salary;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.encryption.Encrypt;

import ch.qos.logback.classic.util.LoggerNameUtil;

@Component("userDao")
public class UserDaoImpl implements UserDao {
	
	SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	

	@Autowired
	EmailAPI email;
	
	
	/* User Login */
	@Transactional()
	@Override
	public AuthTable login(Map<String, String> logincredentials) {
		String username=logincredentials.get("username");
		
		/* encrypts password */
		String password=Encrypt.encrypt(logincredentials.get("password"));
		
		AuthTable authtable=new AuthTable();
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();

		/* getting credentials for the user if exist */
		String hql = "FROM usercredential where username='"+username+"' and password='"+password+"'";
		Query query = session.createQuery(hql);
		List<UserCredential> results = query.list();
		
		String hql2 = "FROM user where username='"+username+"'";
		Query query2 = session.createQuery(hql2);
		List<User> results2 = query2.list();
		
		System.out.println(results+"\t"+results2);
		
		if(!results.isEmpty()&&!results2.isEmpty()){
			UserCredential usercredential=results.get(0);
			User user = results2.get(0);
			
			int randomPIN = (int)(Math.random()*9000)+1000;
			
			/* generate the authentication token for the user */
			String authToken=Encrypt.encrypt(randomPIN+usercredential.getUsername()+usercredential.getPassword());
			
			authtable.setAuthToken(authToken);
			authtable.setUser(user);
				

			/* Deletes all previous entries for the user from autable if Exist*/ 
			hql = "delete FROM authtable where employeeId= :empId" ;
			query = session.createQuery(hql);
			query.setInteger("empId", user.getEmpId());
			query.executeUpdate();
			
			/* creates new entry in the authtable for the user */
			session.save(authtable);
			
			session.getTransaction().commit();
		}
		else{
			
			authtable=(AuthTable)new ResponseEntity<AuthTable>(HttpStatus.NOT_FOUND).notFound();
		}
		return authtable;
	}
	
	
	/* User Logout */
	@Override
	@Transactional
	public ResponseEntity<String> logout(String authToken) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		
		/* Deletes all previous entries for the user from autable if Exist*/ 
		String hql = "delete FROM authtable where authToken= :authToken" ;
		Query query = session.createQuery(hql);
		query.setString("authToken", authToken);
		int numberOfRowsDeleted=query.executeUpdate();
		session.getTransaction().commit();
		session.close();
		
		if(numberOfRowsDeleted>0){
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/* Change Password */
	@Override
	@Transactional
	public ResponseEntity<String> changePassword(String authToken, Map<String, String> passwords) {
		Session session=sessionFactory.openSession();
		
		String oldPassword=passwords.get("oldPassword");
		String newPassword=passwords.get("newPassword");
		ResponseEntity<String> responseEntity;
		session.beginTransaction();
		
		 
		AuthTable authtable=session.get(AuthTable.class, authToken);
		if(authtable!=null){
			User user=authtable.getUser();
			UserCredential usercredential=session.get(UserCredential.class, user.getUserCredential().getUsername());
			String pass=Encrypt.encrypt(oldPassword);
			System.out.println(oldPassword+newPassword+pass+usercredential.getPassword());
			if(pass.equals(usercredential.getPassword())){
				
				usercredential.setPassword(Encrypt.encrypt(newPassword));
				session.update(usercredential);
				
				email.sendEmail(user.getEmail(), "Password Changed" , "Your Password has been changed successfully");
				
				responseEntity=new ResponseEntity<String>(HttpStatus.OK);
			}
			else{
				responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			}
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
		
	}


	/* Forget Password */
	@Override
	public ResponseEntity<String> forgetPassword(Map<String, String> email) {
		Session session=sessionFactory.openSession();
		
		String recoveryEmail=email.get("email");
		
		ResponseEntity<String> responseEntity;
		
		session.beginTransaction();
		
		/* gets user with this email */
		String hql = "FROM user where email= :email" ;
		Query query = session.createQuery(hql);
		query.setString("email", recoveryEmail);
		List<User> users=query.list();
		
		/* checks if user exist or not */
		if(users!=null){
			User user = (User)query.list().get(0);
			UserCredential usercredential = session.get(UserCredential.class, user.getUserCredential().getUsername());
			
			/* generates new password */
			String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@_$!#";
			String pwd = RandomStringUtils.random( 15, characters );
			
			/* stores new password in database */
			usercredential.setPassword(Encrypt.encrypt(pwd));
			session.update(usercredential);
			
			/* sends new password on email */
			String message="The new password is : "+pwd+"\nIt iss strongly recommended to change your password after login with this password";
			this.email.sendEmail(recoveryEmail , "New Password" , message);
			
			responseEntity=new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}


	/* Update Profile */
	@Override
	public ResponseEntity<String> updateProfile(User user, String authToken) {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		Session session=sessionFactory.openSession();
		
		/* check for authToken of User*/
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		if(authtable!=null){
			User loggedUser = session.get(User.class, authtable.getUser().getEmpId());
	
			/* Adding skills to user object */
			List<Skill> skills = new ArrayList<Skill>();
			for(Skill s:user.getSkills()){
				String h="from Skill where skill='"+s.getSkill()+"'";
				Query q=session.createQuery(h);
				skills.addAll(q.list());
				
			}
			user.setSkills(skills);
			
				String about=user.getAbout();
				Address address=user.getAddress();
				Long contact = user.getContact();
				float experience=user.getExperience();
				String maritalStatus=user.getMaritalStatus();
				String name=user.getName();
				List<Skill> skills2=user.getSkills();
			
				if(about!=null&&!about.equals(""))
					loggedUser.setAbout(about);
				if(address!=null)
					loggedUser.setAddress(address);
				if(contact!=null&&contact!=0)
					loggedUser.setContact(contact);
				if(experience!=0.00f)
					loggedUser.setExperience(experience);
				if(maritalStatus!=null&&!maritalStatus.equals(""))
					loggedUser.setMaritalStatus(maritalStatus);
				if(name!=null&&!name.equals(""))
					loggedUser.setName(name);
				if(skills2!=null)
					loggedUser.setSkills(skills2);


			session.update(loggedUser);	
			session.getTransaction().commit();
			session.close();
			
			responseEntity = new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			responseEntity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		return responseEntity;
	}
	
	/* Update Profile */
	@Override
	public User viewProfile(String authToken) {
		User user=null;
		
		Session session=sessionFactory.openSession();
		
		/* check for authToken of User*/
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		if(authtable!=null){
			user=authtable.getUser();
		}
		return user;
	}

}
