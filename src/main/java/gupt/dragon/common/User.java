/*
COPYRIGHT (C) 2019 BY Four Training Departments. ALL RIGHTS RESERVED.
 */

package gupt.dragon.common;

import java.io.Serializable;

/**
 * 用户信息
 */

public final class User implements Serializable {

	private static final long serialVersionUID = -4667734894121828162L;

	private String jobNumber; // 用户工号
	private String userName; // 用户姓名
	private String password; // 用户密码
	private String department; // 用户部门
	private String position; // 用户职位
	private int phoneNumber; // 用户手机号码
	private String mail; // 用户邮箱
	private String power; // 用户权限

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber2) {
		this.jobNumber = jobNumber2;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	@Override
	public String toString() {
		return "User [jobNumber=" + jobNumber + ", userName=" + userName + ", password=" + password + ", department="
				+ department + ", position=" + position + ", phoneNumber=" + phoneNumber + ", mail=" + mail + ", power="
				+ power + "]";
	}

}
