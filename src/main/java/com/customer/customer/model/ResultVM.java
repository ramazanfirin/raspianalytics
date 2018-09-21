package com.customer.customer.model;

public class ResultVM {
	String age;
	String gender;
	String afid;
	Long duration;
	
	
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public ResultVM(String age, String gender, String afid) {
		super();
		this.age = age;
		this.gender = gender;
		this.afid = afid;
	}
	
	public ResultVM(String age, String gender, String afid,Long duration) {
		super();
		this.age = age;
		this.gender = gender;
		this.afid = afid;
		this.duration = duration;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAfid() {
		return afid;
	}
	public void setAfid(String afid) {
		this.afid = afid;
	}
	
	
}
