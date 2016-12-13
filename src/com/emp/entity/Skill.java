package com.emp.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name="skill")
public class Skill {
	
	@Id
	@SequenceGenerator(name="skill_sequence",sequenceName="skill_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="skill_sequence")
	@Column(name="id")
	private int id;
	
	@Column(name="skill")
	private String skill;
	


	@Override
	public String toString() {
		return "Skill [id=" + id + ", skill=" + skill + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

}
