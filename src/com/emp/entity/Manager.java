package com.emp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="manager")
public class Manager{

	@Id
	@Column(name="managerId")
	private int managerId;
	
	@OneToOne
	@JoinColumn(name="projectId")
	private Project project;
	
	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", project=" + project + "]";
	}



	
	
}
