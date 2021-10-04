package com.xrade.models.user;

public class Member {
	
	protected String lastname;
	protected String firstname;
	protected String email;
	protected int point = 0;
	protected MemberGrade grade = MemberGrade.Beginner;
	protected String country;
	protected String phone;
	protected String reference;
	protected String referenced;
	protected String createdAt ="";
	protected boolean verified = false;
	
	protected String password;
	
	protected MemberAccount account;
	
	
	public Member(){
		
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
	}


	public MemberGrade getGrade() {
		return grade;
	}


	public void setGrade(MemberGrade grade) {
		this.grade = grade;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getReferenced() {
		return referenced;
	}


	public void setReferenced(String referenced) {
		this.referenced = referenced;
	}


	public String getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public boolean isVerified() {
		return verified;
	}


	public void setVerified(boolean verified) {
		this.verified = verified;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public MemberAccount getAccount() {
		return account;
	}


	public void setAccount(MemberAccount account) {
		this.account = account;
	}
	
	

}
