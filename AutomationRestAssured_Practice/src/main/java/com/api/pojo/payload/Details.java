package com.api.pojo.payload;

public class Details {

//Initialize the datamember as private
	
private String companyName;
private String emailId;

//Create the constructor
   
    public Details(String companyName, String emailId) {
    	this.companyName = companyName;
    	this.emailId = emailId;
    }

  public String getCompanyName() {
	  return companyName;
  }
  public void setCompanyName(String companyName) {
	  this.companyName =companyName;
  }
  public String getEmailId() {
	  return emailId;
  }
  public void setEmailId(String eemaiId) {
	  this.emailId=emailId;
  }
}
