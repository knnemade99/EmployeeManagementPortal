package com.emp.pojos;

public class ChangePasswordPOJO {
	private String oldPassword;
	private String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "ChangePasswordPOJO [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
	
	
	public boolean validate(){
		if(this.oldPassword!=null&&this.oldPassword!=""&&this.newPassword!=null&&this.newPassword!="")
			return true;
		else
			return false;
	}
}
