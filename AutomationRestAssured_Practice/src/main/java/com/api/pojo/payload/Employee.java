package com.api.pojo.payload;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class Employee {

	//Initialize the datamember as private
	
	private String name;
	private String job;
	private String[] skills;
	private Details details;
	
	//Create the constructor
	
	public Employee(String name, String job, String[] skiils, String comapnyName, String emailId) {
		
		this.name = name;
		this.job =job;
		this.skills = skiils;
		this.details = new Details(comapnyName,emailId);
		
		
	}

	//Create geter and seter method to access the data
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job=job;
	}
	
	public String[] getSkils() {
		return skills;
	}
	public void setSkills(String[] skills) {
		this.skills=skills;
	}
	
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
}
