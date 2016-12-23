package com.emp.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.emp.entity.AuthTable;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.util.EmailAPI;
import com.emp.util.Encrypt;
import com.emp.util.OperationImpl;

@Component("adminDao")
public class AdminDaoImpl implements AdminDao {

	public final SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();

	@Autowired
	EmailAPI email;

	/* Adds new Employee */
	@Transactional
	@Override
	public ResponseEntity<User> addEmployee(User user, String authToken) {
		
		ResponseEntity<User> responseEntity = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);;
		
		final Session session=sessionFactory.openSession();
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@_$!#";
		String password = RandomStringUtils.random( 15, characters );
		
		user.getUserCredential().setPassword(Encrypt.encrypt(password));
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User adminUser=session.get(User.class, authtable.getUser().getEmpId());

		/* Adding skills to user object */
		List<Skill> skills = new ArrayList<Skill>();
		for(Skill s:user.getSkills()){
			String h="from skill where skillName='"+s.getSkillName()+"'";
			Query q=session.createQuery(h);
			if(!q.list().isEmpty())
				skills.addAll(q.list());
		}
		
		/* Adding department to user object */
		if(user.getDepartment()!=null){
			String h="from department where departmentName='"+user.getDepartment().getDepartmentName()+"'";
			Query q=session.createQuery(h);
			if(!q.list().isEmpty())
				user.setDepartment(((Department)q.list().get(0)));
			else
				user.setDepartment(null);
		}

		
		/* Adding project to user object */
		if(user.getProject()!=null){
			String h="from project where projectName='"+user.getProject().getProjectName()+"'";
			Query q=session.createQuery(h);
			if(!q.list().isEmpty())
				user.setProject(((Project)q.list().get(0)));
			else
				user.setProject(null);
		}
	
		user.setSkills(skills);

		if(adminUser.getUsertype().equals("Admin")){
			if(user==null||user.getUserCredential()==null){
				responseEntity= new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			}
			else{
				/* Stores user and UserCredential in database  */
				session.persist(user);
				
				String msg="Welcome to the Urban-Xoriant, an Employee Management Portal designed by Ashish and Kunal.\n\nPlease sign in with the following credentials into the portal- \n Username:"+user.getUserCredential().getUsername()+"\nPassword:"+password+" \nIt is Strongly recommended to change the password after first login";
								
				/* sends Welcome Email */
				email.sendEmail(user.getEmail(), "Welcome" , msg);
				responseEntity= new ResponseEntity<User>(HttpStatus.OK);
				
				/* entry in operation table */
				OperationImpl operationImpl= new OperationImpl();
				operationImpl.addOperation(session, adminUser, "Added Employee "+user.getName());
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
		final	Session session=sessionFactory.openSession();
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
		final	Session session=sessionFactory.openSession();
		
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
		final	Session session=sessionFactory.openSession();
		
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
				
				/* delete from operations table */
				hql = "delete FROM operations where employeeId= :empId" ;
				query2 = session.createQuery(hql);
				query2.setInteger("empId", user.getEmpId());
				query2.executeUpdate();
				
			
				/* delete from user */
				session.delete(user);
				
				/* delete from usercredential */
				session.delete(user.getUserCredential());
				
				/* entry in operation table */
				OperationImpl operationImpl= new OperationImpl();
				operationImpl.addOperation(session, userAdmin, "Deleted Employee "+user.getName());
				
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
		final	Session session=sessionFactory.openSession();
		
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
					
					/* entry in operation table */
					OperationImpl operationImpl= new OperationImpl();
					operationImpl.addOperation(session, userAdmin, "Unlocked employee "+user.getName());
					
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

	/* View All Departments */
	@Override
	public List<Department> viewAllDepartments(String authToken) {
		final	Session session=sessionFactory.openSession();
		List<Department> departmentList =null;

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User user = authtable.getUser();
		if(user.getUsertype().equals("Admin")){
			String hql="from department";
			Query q=session.createQuery(hql);
			departmentList=(List)q.list();
		}
		session.getTransaction().commit();
		session.close();
		return departmentList;
	}
	
	/* View All Projects */
	@Override
	public List<Project> viewAllProjects(String authToken) {
		final	Session session=sessionFactory.openSession();
		List<Project> projectList =null;

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User user = authtable.getUser();
		if(user.getUsertype().equals("Admin")){
			String hql="from project";
			Query q=session.createQuery(hql);
			projectList=(List)q.list();
		}
		session.getTransaction().commit();
		session.close();
		return projectList;
	}
	
	/* View All Skills */
	@Override
	public List<Skill> viewAllSkills(String authToken) {
		final	Session session=sessionFactory.openSession();
		List<Skill> skillList =null;

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User user = authtable.getUser();

			String hql="from skill";
			Query q=session.createQuery(hql);
			skillList=(List)q.list();

		session.getTransaction().commit();
		session.close();
		return skillList;
	}
	
	/*Add new Skill */
	@Transactional
	@Override
	public ResponseEntity<String> addSkill(String authToken , Skill skill) {
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Session session=sessionFactory.openSession();

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User adminUser = authtable.getUser();
		if(adminUser.getUsertype().equals("Admin")){
			session.save(skill);
			
			/* entry in operation table */
			OperationImpl operationImpl= new OperationImpl();
			operationImpl.addOperation(session, adminUser, "Added skill  "+skill.getSkillName());
			
			responseEntity=new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}
	
	/*Add new Department */
	@Transactional
	@Override
	public ResponseEntity<String> addDepartment(String authToken , Department newDepartment) {
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Session session=sessionFactory.openSession();

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User adminUser = authtable.getUser();
		if(adminUser.getUsertype().equals("Admin")){
			session.save(newDepartment);
			responseEntity=new ResponseEntity<String>(HttpStatus.OK);
			
			/* entry in operation table */
			OperationImpl operationImpl= new OperationImpl();
			operationImpl.addOperation(session, adminUser, "Added department  "+newDepartment.getDepartmentName());
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}
	
	/*Add new Project */
	@Transactional
	@Override
	public ResponseEntity<String> addProject(String authToken , Project project) {
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Session session=sessionFactory.openSession();

		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);

		User adminUser = authtable.getUser();
		if(adminUser.getUsertype().equals("Admin")){
			session.save(project);
			
			/* entry in operation table */
			OperationImpl operationImpl= new OperationImpl();
			operationImpl.addOperation(session, adminUser, "Added project  "+project.getProjectName());
			
			responseEntity=new ResponseEntity<String>(HttpStatus.OK);
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}
	
	/* Delete a Project*/
	@Transactional
	@Override
	public ResponseEntity<String> deleteProject(String authToken , int projectId) {
		Session session=sessionFactory.openSession();
		
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		/* check for authToken of admin */
		session.beginTransaction();
		AuthTable authtable=session.get(AuthTable.class, authToken);
		User adminUser=session.get(User.class, authtable.getUser().getEmpId());
		if(adminUser.getUsertype().equals("Admin")){	
			
				/* get The Project */
				String hql = "FROM project where projectId= "+projectId ;
				Query query = session.createQuery(hql);
				Project returnedProject=(Project)query.list().get(0);
			
				/* get user of that Project */
				hql = "FROM user where projectId="+returnedProject.getProjectId() ;
				query = session.createQuery(hql);
				List<User> users=(List<User>)query.list();
				
				/* Deleting project from users */
				for(User user:users){
					user.setProject(null);
					session.persist(user);
				}
			
							
				/* delete from project table */
				if(returnedProject!=null){
					session.delete(returnedProject);
					
				/* entry in operation table */
				OperationImpl operationImpl= new OperationImpl();
				operationImpl.addOperation(session, adminUser, "Deleted Project  "+ returnedProject.getProjectName());
					
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
}
