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
import com.encryption.Encrypt;

public class Main {
	public static void main(String[] args) {
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		
		s.beginTransaction();
		
		/* Create Department */
//		Department dept=new Department();
//		dept.setDepartmentName("HR");
		
		
		
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
//		project.setProjectName("HDS");
//		project.setStartDate(new Date(2016,0,1));
		
		
		
		
		/* create managers */
//		Manager manager=new Manager();
//			User user=s.get(User.class, 1);
//		manager.setManagerId(user.getEmpId());
//		manager.setProject(s.get(Project.class, 3));
		
		
		
		
		/* create users */
		User user=new User();
		user.setAbout("I am Kunal Nemade. I belongs to Indore");
			Address address=new Address();
			address.setCity("Indore");
			address.setCountry("India");
			address.setState("Madhya Pradesh");
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
			Project project=s.get(Project.class, 1);
		user.setProject(project);
			Salary salary=new Salary();
			salary.setBasic(50000);
			salary.setHra(6000f);
			salary.setLta(1250);
		user.setSalary(salary);
		user.setUsertype("Admin");
			UserCredential uc=new UserCredential();
			uc.setUsername("admin");
			uc.setPassword(Encrypt.encrypt("1234"));
		user.setUserCredential(uc);
			List<Skill> skills=new ArrayList<Skill>();
			skills.add(s.get(Skill.class, 1));
			skills.add(s.get(Skill.class, 2));
		user.setSkills(skills);

		
		
		
		/* Create Skills */
//		Skill skill=new Skill();
//				skill.setSkillName("Ruby");
		
		
		
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
