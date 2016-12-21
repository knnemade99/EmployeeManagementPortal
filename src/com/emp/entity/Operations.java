package com.emp.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name="operations")
public class Operations {
	@Id
	@Column(name="operationId")
	@SequenceGenerator(name="operation_sequence",sequenceName="operation_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="operation_sequence")
	private int operationId;
	
	
	@OneToOne
	@JoinColumn(name="employeeId")
	private User user;
	
	@Column(name="operation")
	private String operation;

	@Column(name="timeStamp")
	private Timestamp timestamp;

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Operations [operationId=" + operationId + ", user=" + user + ", operation=" + operation + ", timestamp="
				+ timestamp + "]";
	}


}
