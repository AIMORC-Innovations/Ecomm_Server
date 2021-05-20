package com.aimorc.ajax;

import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
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
	public Timestamp lastlogin;
	public String created_on;

	public int getuserId() {
		return userId;
	}

	public void setuserId(int userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public String setFirstname(String firstname) {
		return this.firstname = firstname;
	}

	public Timestamp getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Timestamp lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String string) {
		this.created_on = string;
	}

	public String getLastname() {
		return lastname;
	}

	public String setLastname(String lastname) {
		return this.lastname = lastname;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public String setPhonenum(String phonenum) {
		return this.phonenum = phonenum;
	}

	public String getAddress() {
		return address;
	}

	public String setAddress(String address) {
		return this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public String setGender(String gender) {
		return this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public String setDob(String dob) {
		return this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public String setUsername(String username) {
		return this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String setPassword(String password) {
		return this.password = password;
	}

	public Account(int userId, String firstname, String lastname, String phonenum, String address, String gender,
			String dob, String username, String password, Timestamp lastlogin, String created_on) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenum = phonenum;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
		this.username = username;
		this.password = password;
		this.lastlogin = lastlogin;
		this.created_on = created_on;
	}

	public Account() {

	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", phonenum="
				+ phonenum + ", address=" + address + ", gender=" + gender + ", dob=" + dob + ", username=" + username
				+ ", password=" + password + "]";
	}

	public JSONObject getJSONObject() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("Firstname", firstname);
		obj.put("Lastname", lastname);
		obj.put("Date", dob);
		obj.put("Gender", gender);
		obj.put("Mobile", phonenum);
		obj.put("Address", address);
		return obj;
	}
}
