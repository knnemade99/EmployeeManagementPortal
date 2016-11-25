package com.employeemanagementportal.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Salary {

	@Column(name="basic")
	private float basic;
	
	@Column(name="hra")
	private float hra;
	
	@Column(name="lta")
	private float lta;

	public float getBasic() {
		return basic;
	}
	public void setBasic(float basic) {
		this.basic = basic;
	}
	public float getHra() {
		return hra;
	}
	public void setHra(float hra) {
		this.hra = hra;
	}
	public float getLta() {
		return lta;
	}
	public void setLta(float lta) {
		this.lta = lta;
	}
	@Override
	public String toString() {
		return "Salary [basic=" + basic + ", hra=" + hra + ", lta=" + lta + "]";
	}
	
	
}
