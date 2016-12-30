package com.emp.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.emp.dao.UserDao;
import com.emp.entity.Address;
import com.emp.entity.AuthTable;
import com.emp.entity.Operations;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.exceptions.ManualException;
import com.emp.pojos.ChangePasswordPOJO;
import com.emp.pojos.UserCredentialPOJO;
import com.emp.util.EmailAPI;
import com.emp.util.Encrypt;
import com.emp.util.OperationImpl;

@Component("userDao")
@PropertySource({
	"classpath:email.properties"
})
public class UserDaoImpl implements UserDao {
	
	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	EmailAPI email;

	@Autowired
	private Environment env;
	
	/* User Login */
	@Override
	public AuthTable login(UserCredentialPOJO logincredentials) throws ManualException {
		AuthTable authtable=new AuthTable();
		Session session=sessionFactory.openSession();
		try{
				logincredentials.getUsername();
				session.beginTransaction();
		
				/* getting credentials for the user */
				String hql = "FROM usercredential where username='"+logincredentials.getUsername()+"' and password='"+logincredentials.getPassword()+"'";
				Query query = session.createQuery(hql);
				List<UserCredential> results = query.list();
				
				/* checks if usercredentials exist  */
				if(!results.isEmpty()){
					
					/* getting user record for the user credential */
					String hql2 = "FROM user where username='"+results.get(0).getUsername()+"'";
					Query query2 = session.createQuery(hql2);
					List<User> results2 = query2.list();
					
					/* checks if user exists or not */
					if(!results2.isEmpty()){
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
					}
					else{
						authtable=null;
					}
				}
				else{
					authtable=null;
				}
		}
		catch(Exception e){
			System.out.println(e.toString());
			authtable=null;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return authtable;
		}
	}
	
	
	/* User Logout */
	@Override
	public ResponseEntity<String> logout(String authToken) throws DataAccessException , ManualException {
		final	Session session=sessionFactory.openSession();
		ResponseEntity responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		try{
			session.beginTransaction();
			
			/* Deletes all previous entries for the user from autable if Exist*/ 
			String hql = "delete FROM authtable where authToken= :authToken" ;
			Query query = session.createQuery(hql);
			query.setString("authToken", authToken);
			int numberOfRowsDeleted=query.executeUpdate();
					
			if(numberOfRowsDeleted>0){
				responseEntity= new ResponseEntity<String>(HttpStatus.OK);
			}
			else{
				responseEntity= new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e){
			responseEntity=null;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return responseEntity;
		}
	}
	
	
	/* Change Password */
	@Override
	public ResponseEntity<String> changePassword(String authToken, ChangePasswordPOJO passwords) throws DataAccessException, ManualException {
		final	Session session=sessionFactory.openSession();
		
		ResponseEntity<String> responseEntity =new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		try{
			session.beginTransaction();
			
			AuthTable authtable=session.get(AuthTable.class, authToken);
			if(authtable!=null){
				User user=authtable.getUser();
				UserCredential usercredential=session.get(UserCredential.class, user.getUserCredential().getUsername());
				
				if(passwords.getOldPassword().equals(usercredential.getPassword())){
					
					usercredential.setPassword(passwords.getNewPassword());
					session.update(usercredential);
					
					email.sendEmail(user.getEmail(), env.getProperty("email.password.changed.subject") , env.getProperty("email.password.changed.content"));
					
					/* entry in operation table */
					OperationImpl operationImpl= new OperationImpl();
					operationImpl.addOperation(session, user, "Changed Password");
					
					responseEntity=new ResponseEntity<String>(HttpStatus.OK);
				}
				else{
					responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
				}
			}
			else{
				responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			
		}
		catch(Exception e){
			responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return responseEntity;
		}
	}


	/* Forget Password */
	@Override
	public ResponseEntity<String> forgetPassword(String email) throws DataAccessException, ManualException{
		final	Session session=sessionFactory.openSession();
		
		String recoveryEmail=email;
		
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		System.out.println(recoveryEmail);
		try{
			session.beginTransaction();
			
			/* gets user with this email */
			String hql = "FROM user where email= :email" ;
			Query query = session.createQuery(hql);
			query.setString("email", recoveryEmail);
			List<User> users=query.list();
			
			/* checks if user exist or not */
			if(!users.isEmpty()){
				User user = (User)query.list().get(0);
				UserCredential usercredential = session.get(UserCredential.class, user.getUserCredential().getUsername());
				
				/* generates new password */
				String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@_$!#";
				String pwd = RandomStringUtils.random( 15, characters );
				
				/* stores new password in database */
				usercredential.setPassword(Encrypt.encrypt(pwd));
				session.update(usercredential);
				
				/* sends new password on email */
				String message=env.getProperty("email.password.forgot.content")+pwd;
				this.email.sendEmail(recoveryEmail , env.getProperty("email.password.forgot.subject") , message);
				
				/* entry in operation table */
				OperationImpl operationImpl= new OperationImpl();
				operationImpl.addOperation(session, user, "Password Reset");
				
				responseEntity=new ResponseEntity<String>(HttpStatus.OK);
			}
			else{
				responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
				throw new ManualException("email.notExist", env.getProperty("email.notExist"));
			}
		}
		catch(Exception e){
			responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return responseEntity;
		}
	}


	/* Update Profile */
	@Override
	public ResponseEntity<String> updateProfile(User user, String authToken) throws DataAccessException , ManualException {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		final	Session session=sessionFactory.openSession();
		
		try{
			/* check for authToken of User*/
			session.beginTransaction();
			AuthTable authtable=session.get(AuthTable.class, authToken);
			if(authtable!=null){
				User loggedUser = session.get(User.class, authtable.getUser().getEmpId());
				
				if(!user.getSkills().isEmpty()){
					/* Adding skills to user object */
					List<Skill> skills = new ArrayList<Skill>();
					for(Skill s:user.getSkills()){
						String h="from Skill where skillName='"+s.getSkillName()+"'";
						Query q=session.createQuery(h);
						skills.addAll(q.list());
						
					}
					user.setSkills(skills);
				}
				else{
					user.setSkills(loggedUser.getSkills());
				}
				
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
	
					
					System.out.println("User to update"+user);
				session.update(loggedUser);
				
	
				/* entry in operation table */
				OperationImpl operationImpl= new OperationImpl();
				operationImpl.addOperation(session, loggedUser, "Updated Profile");	
				
				responseEntity = new ResponseEntity<String>(HttpStatus.OK);
			}
			else{
				responseEntity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception e){
			responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return responseEntity;
		}
	}
	
	/* View Profile */
	@Override
	public User viewProfile(String authToken) throws DataAccessException, ManualException {
		User user=null;
		
		final	Session session=sessionFactory.openSession();
		
		try{
			/* check for authToken of User*/
			session.beginTransaction();
			AuthTable authtable=session.get(AuthTable.class, authToken);
			if(authtable!=null){
				user=authtable.getUser();
			}
		}
		catch(Exception e){
			
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return user;
		}
	}
	
	/* View History */
	@Override
	public ArrayList<Operations> viewHistory(String authToken) throws DataAccessException, ManualException {
		ArrayList<Operations> operations=null;
		
		final	Session session=sessionFactory.openSession();
		
		try{
			/* check for authToken of User*/
			session.beginTransaction();
			AuthTable authtable=session.get(AuthTable.class, authToken);
			
			if(authtable!=null){
				User user = authtable.getUser();
				
					/* getting operations of user */
					String h="from operations where employeeId="+user.getEmpId();
					Query q=session.createQuery(h);
					if(!q.list().isEmpty())
						operations=(ArrayList<Operations>)q.list();
					else
						user.setDepartment(null);
			}
		}
		catch(Exception e){
			operations=null;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return operations;
		}
	}

}
