package com.emp.daoImpl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.AdminDao;
import com.emp.entity.AuthTable;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;
import com.encryption.Encrypt;

@Component("admindao")
public class AdminDaoImpl implements AdminDao {

	SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	
	@Transactional
	@Override
	public ResponseEntity<User> addEmployee(UserCredentials usercredentials, String authToken) {
		Session session=sessionFactory.openSession();
		usercredentials.setPassword(Encrypt.encrypt(usercredentials.getPassword()));
		User user=null;
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		UserCredentials usercredentials2=session.get(UserCredentials.class, authtable.getUser().getEmpId());
		if(usercredentials2.getUsertype().equals("Admin")){
			System.out.println("inside if statement");
			session.save(usercredentials.getUser());
			session.save(usercredentials);
			System.out.println("after if statement");
			user= usercredentials.getUser();
		}
		session.getTransaction().commit();
		session.close();
		if(user==null){
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	@Override
	public AuthTable login(Map<String, String> logincredentials) {
		String username=logincredentials.get("username");
		
		/* encrypts password */
		String password=Encrypt.encrypt(logincredentials.get("password"));
		
		AuthTable authtable=new AuthTable();
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();

		/* getting credentials for the user if exist */
		String hql = "FROM usercredentials where username='"+username+"' and password='"+password+"'";
		Query query = session.createQuery(hql);
		List results = query.list();
		
		UserCredentials usercredentials=(UserCredentials)results.get(0);
		
		if(!results.isEmpty()){
			int randomPIN = (int)(Math.random()*9000)+1000;
			
			/* generaate the authentication token for the user */
			String authToken=Encrypt.encrypt(randomPIN+usercredentials.getUsername()+usercredentials.getPassword());
			
			authtable.setAuthToken(authToken);
			authtable.setUser(usercredentials.getUser());
				

			/* Deletes all previous entries for the user from autable if Exist*/ 
			hql = "delete FROM authtable where employeeId= :empId" ;
			query = session.createQuery(hql);
			query.setInteger("empId", usercredentials.getUser().getEmpId());
			query.executeUpdate();
			
			/* creates new entry in the auth table for the user */
			session.save(authtable);
			
			session.getTransaction().commit();
		}
		return authtable;
	}

	
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


	@Override
	public List<UserCredentials> viewAllEmployees(String authToken) {
		Session session=sessionFactory.openSession();
		List<UserCredentials> userlist =null;
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		UserCredentials usercredentials=session.get(UserCredentials.class, authtable.getUser().getEmpId());
		if(usercredentials.getUsertype().equals("Admin")){
			Criteria ct=session.createCriteria(UserCredentials.class);
			ct.setProjection( Projections.distinct( Projections.projectionList().add( Projections.property("user"), "user").add(Projections.property("username"), "username")));
//			String hql="select e.user as user,e.password as password from usercredentials e";
//			Query q=session.createQuery(hql);
			userlist=ct.list();
		}
		session.getTransaction().commit();
		session.close();
		return userlist;
	}

}
