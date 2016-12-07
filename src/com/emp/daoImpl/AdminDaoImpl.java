package com.emp.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.AdminDao;
import com.emp.email.EmailAPI;
import com.emp.entity.AuthTable;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.encryption.Encrypt;

@Component("adminDao")
public class AdminDaoImpl implements AdminDao {

	SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();

	@Autowired
	EmailAPI email;

	/* Adds new Employee */
	@Transactional
	@Override
	public ResponseEntity<User> addEmployee(User user, String authToken) {
		
		ResponseEntity<User> responseEntity = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);;
		
		Session session=sessionFactory.openSession();
		String password=user.getUserCredential().getPassword();
		user.getUserCredential().setPassword(Encrypt.encrypt(user.getUserCredential().getPassword()));
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User adminUser=session.get(User.class, authtable.getUser().getEmpId());

		/* Adding skills to user object */
		List<Skill> skills = new ArrayList<Skill>();
		for(Skill s:user.getSkills()){
			String h="from Skill where skill='"+s.getSkill()+"'";
			Query q=session.createQuery(h);
			skills.addAll(q.list());
			
		}
		user.setSkills(skills);

		if(adminUser.getUsertype().equals("Admin")){
			if(user==null||user.getUserCredential()==null){
				responseEntity= new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			else{
				/* Stores user and usercredentials in database  */
				session.persist(user);
				
				String msg="Welcome to the Employee Management Portal.\nPlease sign in with the following credentials into the portal\nUsername:"+user.getUserCredential().getUsername()+"\nPassword:"+password+"\nIt is Strongly recommended to change the password after first login";
								
				/* sends Welcome Email */
				email.sendEmail(user.getEmail(), "Welcome" , msg);
				responseEntity= new ResponseEntity<User>(HttpStatus.OK);
			}
		}
		else{
			responseEntity= new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}

	
	/* View All Employees */
	@Override
	public List<UserCredential> viewAllEmployees(String authToken) {
		Session session=sessionFactory.openSession();
		List<UserCredential> userlist =null;

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User user = authtable.getUser();
		if(user.getUsertype().equals("Admin")){
			String hql="from user";
			Query q=session.createQuery(hql);
			userlist=(List)q.list();
		}
		session.getTransaction().commit();
		session.close();
		return userlist;
	}
	
	/* View Employee by Id */
	@Override
	public User viewEmployee(int employeeId,String authToken) {
		Session session=sessionFactory.openSession();
		
		User user=null;;
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User userAdmin=session.get(User.class,authtable.getUser().getEmpId());
		if(userAdmin.getUsertype().equals("Admin")){

			User user2 = session.get(User.class, employeeId);
			if(user2!=null)
				user=user2;
			else
				user=null;
		}
		session.getTransaction().commit();
		session.close();
		return user;
	}
	
	/* Delete Employee by Id */
	@Override
	public ResponseEntity<String> deleteEmployee(int employeeId,String authToken) {
		Session session=sessionFactory.openSession();
		
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User userAdmin=session.get(User.class, authtable.getUser().getEmpId());
		if(userAdmin.getUsertype().equals("Admin")){
			User user = session.get(User.class, employeeId);
			if(user!=null){
				
				/* delete from user_skill */
				String sql = "delete FROM user_skill WHERE user_employeeId = :employee_id";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("employee_id", user.getEmpId() );
				query.executeUpdate();
				
				/* delete from authtable */
				String hql = "delete FROM authtable where employeeId= :empId" ;
				Query query2 = session.createQuery(hql);
				query2.setInteger("empId", user.getEmpId());
				query2.executeUpdate();
				
			
				/* delete from user */
				session.delete(user);
				
				/* delete from usercredential */
				session.delete(user.getUserCredential());
				
				responseEntity=new ResponseEntity<String>(HttpStatus.OK);
			}
			else
				responseEntity=new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}
	
	
	/* Unlock Employee by Id */
	@Override
	public ResponseEntity<String> unlockEmployee(int employeeId,String authToken) {
		Session session=sessionFactory.openSession();
		
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User userAdmin=session.get(User.class, authtable.getUser().getEmpId());
		if(userAdmin.getUsertype().equals("Admin")){
			User user = session.get(User.class, employeeId);
			if(user!=null){
				if(!user.getLockStatus().equals("unlock")){
					user.setLockStatus("unlock");
					responseEntity=new ResponseEntity<String>(HttpStatus.OK);
				}
				else{
					responseEntity=new ResponseEntity<String>(HttpStatus.ALREADY_REPORTED);
				}
			}
			else
				responseEntity=new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}

}
