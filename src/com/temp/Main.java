package com.temp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.emp.entity.Address;
import com.emp.entity.Department;
import com.emp.entity.Project;
import com.emp.entity.Salary;
import com.emp.entity.Skill;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.util.Encrypt;

public class Main {
	public static void main(String[] args) {
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		
		s.beginTransaction();
		
		/* Create Department */
		Department dept=new Department();
		dept.setDepartmentName("ADMIN");
		
		
		
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
//		project.setBudget(556000);
//		project.setEndDate(new Date(2018,8,20));
//		project.setProjectName("SAP DataMart");
//		project.setStartDate(new Date(2009,9,19));
		
		
		
		
		/* create managers */
//		Manager manager=new Manager();
//			User user=s.get(User.class, 1);
//		manager.setManagerId(user.getEmpId());
//		manager.setProject(s.get(Project.class, 3));
		
		
		
		
		/* create users */
		User user=new User();
		user.setAbout("I am Adminitrator of this Employee Management Portal made for Xoriant Solutions Pune");
			Address address=new Address();
			address.setCity("Pune");
			address.setCountry("India");
			address.setState("Maharashtra");
			address.setZip(411030);
		user.setAddress(address);
		user.setContact(7697599287l);
			Department department=s.get(Department.class, 1);
		user.setDepartment(department);
		user.setDesignation("Administrator");
		user.setDob(new Date(1994-1900,7,31));
		user.setDoj((new Date(2016-1900,5,20)));
		user.setEmail("urban.xoriant@gmail.com");
		user.setExperience(10);
		user.setGender("Male");
		user.setLockStatus("unlock");
		user.setMaritalStatus("Unmarried");
		user.setName("Urban Xoriant");
//			Project project=s.get(Project.class, 1);
//		user.setProject(project);
			Salary salary=new Salary();
			salary.setBasic(5000000);
			salary.setHra(12000f);
			salary.setLta(12500);
		user.setSalary(salary);
		user.setUsertype("Admin");
			UserCredential uc=new UserCredential();
			uc.setUsername("urban");
			uc.setPassword(Encrypt.encrypt("123456"));
		user.setUserCredential(uc);
			List<Skill> skills=new ArrayList<Skill>();
			skills.add(s.get(Skill.class, 1));
			skills.add(s.get(Skill.class, 2));
		user.setSkills(skills);

		
		
		
		/* Create Skills */
		Skill skill=new Skill();
				skill.setSkillName("Spring");
		
		
		
		/* Create credentials */
//		User user=s.get(User.class, 1);
//		UserCredential uc=new UserCredential();
//		uc.setUsername(username);
//		uc.setUsername("admin");
//		uc.setPassword(Encrypt.encrypt("1234"));
//		uc.setUsertype("Admin");
		
		
		
		/* create authtable*/
//		User user=s.get(User.class, 1);
//		AuthTable at=new AuthTable();
//		at.setAuthToken("dffhjddf4vf544fe65");
//		at.setUser(user);
		
		

		try{	
			s.save(uc);
			s.save(user);
//			System.out.println(user);
//			s.save(skill);
//			s.persist(skill);
			
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
