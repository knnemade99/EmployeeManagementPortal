package com.temp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.employeemanagementportal.entity.AuthTable;
import com.employeemanagementportal.entity.User;
import com.employeemanagementportal.entity.UserCredentials;

public class Main {
	public static void main(String[] args) {
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		

		
		/* Create Department */
//		Department d1=new Department();
//		d1.setDeptName("Accounts");
		
		
		
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
//		project.setBudget(500000);
//		project.setEndDate(new Date());
//		project.setProjectName("SAP DMart");
//		project.setStartDate(new Date());
		
		
		
		
		/* create managers */
//		Manager manager=new Manager();
//			User user=s.get(User.class, 1);
//		manager.setManagerId(user.getEmpId());
//		manager.setProject(s.get(Project.class, 3));
		
		
		
		
		/* create users */
//		User user=new User();
//		user.setAbout("I am Kunal");
//			Address address=new Address();
//			address.setCity("Puneww");
//			address.setCountry("India");
//			address.setState("MH");
//			address.setZip(1234);
//		user.setAddress(address);
//		user.setContact(1234);
//			Department department=s.get(Department.class, 2);
//		user.setDepartment(department);
//		user.setDesignation("ASE");
//		user.setDob(new Date());
//		user.setDoj(new Date());
//		user.setEmail("km@m.com");
//		user.setExperience(12);
//		user.setGender("Maleww");
//		user.setLockStatus("unlock");
//		user.setMaritalStatus("single");
//		user.setName("Kunal");
//			Project project=s.get(Project.class, 3);
//		user.setProject(project);
//			Salary salary=new Salary();
//			salary.setBasic(120000);
//			salary.setHra(23000.12f);
//			salary.setLta(125000);
//		user.setSalary(salary);
//			List<Skill> skills=new ArrayList<Skill>();
//			skills.add(s.get(Skill.class, 1));
//			skills.add(s.get(Skill.class, 3));
//		user.setSkills(skills);

		
		
		
		/* Create Skills */
//		Skill skill=new Skill();
//				skill.setSkill("Python");
		
		
		
		/* Create credentials */
//		User user=s.get(User.class, 1);
//		UserCredentials uc=new UserCredentials();
//		uc.setUser(user);
//		uc.setUsername("knnemade99");
//		uc.setPassword("Kunal");
//		uc.setUsertype("Admin");
		
		
		
		/* create authtable*/
//		User user=s.get(User.class, 1);
//		AuthTable at=new AuthTable();
//		at.setAuthToken("dffhjddf4vf544fe65");
//		at.setUser(user);
		
		

		try{	
			s.beginTransaction();
//			s.save(uc);
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
