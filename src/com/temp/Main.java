package com.temp;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.emp.entity.Address;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Salary;
import com.emp.entity.User;
import com.emp.entity.UserCredentials;
import com.encryption.Encrypt;

public class Main {
	public static void main(String[] args) {
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		

		
		/* Create Department */
//		Department dept=new Department();
//		dept.setDeptName("Sales");
		
		
		
		/* Create Users */
//		User user=new User();
//		user.setAbout("Hi I am Kunal");
//			Address address=new Address();
//			address.setCity("Pune");
//			address.setCountry("India");
//			address.setState("MH");
//			address.setZip(12344565);
//		user.setAddress(address);
//		user.setContact(1234567894);
//		user.setDepartment(d1);
		
		
		
		
		/* Create Projects */
//		Project project =new Project();
//		project.setBudget(1000000);
//		project.setEndDate(new Date(2016,11,31));
//		project.setProjectName("SAP DataMart");
//		project.setStartDate(new Date(2016,0,1));
		
		
		
		
		/* create managers */
//		Manager manager=new Manager();
//			User user=s.get(User.class, 1);
//		manager.setManagerId(user.getEmpId());
//		manager.setProject(s.get(Project.class, 3));
		
		
		
		
		/* create users */
		User user=new User();
		user.setAbout("I am Kunal");
			Address address=new Address();
			address.setCity("Pune");
			address.setCountry("India");
			address.setState("Maharashtra");
			address.setZip(452010);
		user.setAddress(address);
		user.setContact(7697599287l);
			Department department=s.get(Department.class, 1);
		user.setDepartment(department);
		user.setDesignation("Administrator");
		user.setDob(new Date(1994,7,31));
		user.setDoj(new Date());
		user.setEmail("knnemade99@gmail.com");
		user.setExperience(10);
		user.setGender("Male");
		user.setLockStatus("unlock");
		user.setMaritalStatus("single");
		user.setName("Kunal");
			Project project=null;
		user.setProject(project);
			Salary salary=new Salary();
			salary.setBasic(50000);
			salary.setHra(6000f);
			salary.setLta(1250);
		user.setSalary(salary);

		user.setSkills(null);

		
		
		
		/* Create Skills */
//		Skill skill=new Skill();
//				skill.setSkill("Python");
		
		
		
		/* Create credentials */
//		User user=s.get(User.class, 1);
		UserCredentials uc=new UserCredentials();
		uc.setUser(user);
		uc.setUsername("admin");
		uc.setPassword(Encrypt.encrypt("1234"));
		uc.setUsertype("Admin");
		
		
		
		/* create authtable*/
//		User user=s.get(User.class, 1);
//		AuthTable at=new AuthTable();
//		at.setAuthToken("dffhjddf4vf544fe65");
//		at.setUser(user);
		
		

		try{	
			s.beginTransaction();
			s.save(user);
			s.save(uc);
		}
		catch(Exception e){
			s.getTransaction().rollback();
		}
		finally{
			s.getTransaction().commit();
			s.close();
			sf.close();
		}
	}
}
