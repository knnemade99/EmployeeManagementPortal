package com.emp.util;

import java.sql.Timestamp;

import org.hibernate.Session;

import com.emp.entity.Operations;
import com.emp.entity.User;

public class OperationImpl {
	
	/* Adds new operation */
	public void addOperation(Session session, User user , String operationDescription){
		Operations operation = new Operations();
		operation.setUser(user);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		operation.setTimestamp(timestamp);
		operation.setOperation(operationDescription);
		
		session.persist(operation);
	}
}
