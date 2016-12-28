/**
 * Copyright (c) 2013, SAP Mobile Services 
 * One Sybase Drive, Dublin, CA 94568 
 * 925-236-5000
 * http://www.sap.com
 * All rights reserved.
 * 
 * This file contains unpublished proprietary source code of SAP Mobile 
 * Services. The material in this file is the exclusive confidential property 
 * of SAP Mobile Services, is intended for internal use only within SAP Mobile 
 * Services, and is protected by copyright law.  The copyright notice found 
 * above does not evidence any actual or intended publication of this source 
 * material.  Any reproduction or distribution of the material in this file, 
 * either in whole or in part, without the explicit prior written approval of 
 * SAP Mobile Services is prohibited and will be prosecuted to the maximum 
 * extent possible under the law.
 */

package com.emp.exceptions;

@SuppressWarnings("serial")
public class ManualException extends Exception {

	/**
	 * id : error id
	 */
	private String id;

	/**
	 * error description
	 */
	private String description;

	/** No-argument Constructor */
	public ManualException() {
		super();
	}

	/**
	 * 
	 * @param id : error code
	 */
	public ManualException(final String id) {
		super(id);
		this.setId(id);
	}
	/**
	 * 
	 * @param message
	 * @param Throwable
	 */
	public ManualException(Throwable cause, String id, final String message) {
		super(message);
		this.setId(id);
		this.setDescription(message);
		this.initCause(cause);
	}

	/**
	 * 
	 * @param id : error code
	 * @param message : message
	 */
	public ManualException(final String id, final String message) {
		super(message);
		this.setId(id);
		this.setDescription(message);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
