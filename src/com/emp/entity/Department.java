package com.emp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="department")
public class Department {
	@Id
	@Column(name="departmentId")
	@SequenceGenerator(name="department_sequence",sequenceName="department_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="department_sequence")
	private int departmentId;
	
	@Column(name="departmentName" , unique=true)
	private String departmentName;
	
	public int getDeptId() {
		return departmentId;
	}
	public void setDeptId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	}



}
