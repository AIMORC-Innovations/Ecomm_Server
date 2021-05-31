package com.aimorc.postgreSQL;





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

	private String productName;
	private String productDescription;
	private Float productPrice;
	private int product_id;

	
	


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public Float getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}



	


	public String getProductname() {
		return productName;
	}


	public void setProductname(String productName) {
		this.productName = productName;
	}


	public String getProductdescription() {
		return productDescription;
	}


	public void setProductdescription(String productDescription) {
		this.productDescription = productDescription;
	}


	public Account(int userId, String firstname, String lastname, String phonenum, String address, String gender,  String dob ) {
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phonenum = phonenum;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
	
		
	}

	
	public Account() {
		
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
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
