package com.emp.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name="skill")
@OnDelete(action=OnDeleteAction.CASCADE)
public class Skill {
	
	@Id
	@SequenceGenerator(name="skill_sequence",sequenceName="skill_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="skill_sequence")
	@Column(name="id")
	private int id;
	
	@Column(name="skillName",unique=true)
	private String skillName;
	


	@Override
	public String toString() {
		return "Skill [id=" + id + ", skillName=" + skillName + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		id = id;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}
