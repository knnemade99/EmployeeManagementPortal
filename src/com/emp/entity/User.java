package com.emp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name="user")
@DynamicUpdate(value=true)
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
	
	@Id
	@SequenceGenerator(name="user_sequence",sequenceName="user_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_sequence")
	@Column(name="employeeId")
	private int empId;
	
	@ManyToMany(fetch = FetchType.EAGER )
	@JoinTable(name="user_skill")
	private List<Skill> skills = new ArrayList<Skill>();
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="username")
	@NotNull
	private UserCredential userCredential;
	
	@Embedded
	private Address address;
	
	@Embedded
	private Salary salary;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="projectId")
	private Project project; 
	
	@Column(name="name")
	private String name;
	
	@Column(name="email",unique=true)
	private String email;
	
	@Column(name="about")
	private String about;
	
	@Column(name="contact")
	private long contact;
	
	@OneToOne
	@JoinColumn(name="departmentId")
	private Department department;
	
	@Column(name="designation")
	private String designation;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateOfBirth")
	private Date dob;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateOfJoining")
	private Date doj;
	
	@Column(name="lockStatus")
	private String lockStatus;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="maritalStatus")
	private String maritalStatus;
	
	@Column(name="experience")
	private float experience;
	
	@Column(name="managerId")
	private int managerId;
	
	@Column(name="usertype")
	private String usertype;
	

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	public Salary getSalary() {
		return salary;
	}
	public void setSalary(Salary salary) {
		this.salary = salary;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public float getExperience() {
		return experience;
	}
	public void setExperience(float experience) {
		this.experience = experience;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	public UserCredential getUserCredential() {
		return userCredential;
	}
	public void setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
	}
	@Override
	public String toString() {
		return "User [empId=" + empId + ", skills=" + skills + ", userCredential=" + userCredential + ", address="
				+ address + ", salary=" + salary + ", project=" + project + ", name=" + name + ", email=" + email
				+ ", about=" + about + ", contact=" + contact + ", department=" + department + ", designation="
				+ designation + ", dob=" + dob + ", doj=" + doj + ", lockStatus=" + lockStatus + ", gender=" + gender
				+ ", maritalStatus=" + maritalStatus + ", experience=" + experience + ", managerId=" + managerId
				+ ", usertype=" + usertype + "]";
	}

		
}
