package com.aimorc.postgreSQL;

import java.util.Date;

import org.json.simple.JSONObject;

public class Account {

	public int userId;
	public String firstname;
	public String lastname;
	public String phonenum;
	public String address;
	public String gender;
	public String dob;
	public String username;
	public String password;
	
	public Account(int userId, String firstname, String lastname, String phonenum, String address, String gender,  String dob, String username,String password ) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenum = phonenum;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
		this.username = username;
		this.password = password;
		
	}

	
	public Account() {
		// TODO Auto-generated constructor stub
	}


	public int getuserId() {
		return userId;
	}


	public void setuserId(int userId) {
		this.userId = userId;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getPhonenum() {
		return phonenum;
	}


	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Account [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", phonenum="
				+ phonenum + ", address=" + address + ", gender=" + gender + ", dob=" + dob + ", username=" + username
				+ ", password=" + password + "]";
	}

	
}
