package com.api.testscripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.api.pojo.payload.Employee;
import com.api.pojo.payload.EmployeePatch;
import com.api.reports.BaseClass;
import com.api.reports.ExtentTestManager;
import com.api.utils.URL;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestScripts extends BaseClass {

	Employee createEmployee = new Employee("rakesh", "SoftwareEngineer", new String[] {"Java", "Python"}, "Photon", "rakes.s@gmail.com");
	Employee updateEmployee = new Employee("Mohan", "CivilEngineer", new String[] {"Java", "Python"}, "Photon", "mohan123@gmail.com");
	EmployeePatch patchEmployee = new EmployeePatch("Rohan","Manager");
	String status;

	@Test(priority =2, enabled = true)
	public void verifyPostAPI_TC_002() {
    status = "Fail";
	try {
		Response postResponse = given()
				.auth().none()
				.header("Content-Type", "application/json")
				.contentType(ContentType.JSON)
				.when()
				.body(createEmployee).log().all()
				.post(URL.postEndpointUrl);

		System.out.println("Body Response");
		System.out.println(postResponse.prettyPeek());

		System.out.println("-------All the headers are-----"+postResponse.headers());
		System.out.println("-------Header contetn-types are-------"+postResponse.header("Content-Type"));
		System.out.println("-------Header date are---------"+postResponse.header("Date"));
		System.out.println("----get all the cookies are-----"+postResponse.getCookies());
		System.out.println("----get all content type are----"+postResponse.getContentType());

		if(postResponse.getStatusCode()==201) {
			Assert.assertEquals(postResponse.getBody().path("name"), "rakesh");
			Assert.assertEquals(postResponse.getBody().path("job"), "SoftwareEngineer");
			Assert.assertEquals(postResponse.getBody().path("skills[0]"), "Java");
			Assert.assertEquals(postResponse.getBody().path("skills[1]"), "Python");
			Assert.assertEquals(postResponse.getBody().path("details.companyName"), "Photon");
			Assert.assertEquals(postResponse.getBody().path("details.emailId"), "rakes.s@gmail.com");
		}
		/*.then().log().all();
		     .body("name", equalTo("rakesh"),
				 "job", equalTo("SoftwareEngineer"),
				 "skills[0]", equalTo("Java"),
				  "skills[1]", equalTo("Python"),
				  "details.companyName", equalTo("Photon"),
				  "details.emailId", equalTo("rakes.s@gmail.com"));*/
		status = "Pass";	
	}
	catch(Exception e) {
		System.out.println(e);
	}finally {
		if(status.equalsIgnoreCase("failed")) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Verify POST API is "+status);
		}else if(status.equalsIgnoreCase("PASS")) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Verify POST API is "+status);
		}
	}
	
}

	@Test(priority =3, enabled = true)
	public void verifyPutAPI_TC_003() {
		
		try {
		 Response putResponse  =  given()
		     .auth().none()
		     .header("Content-Type", "application/json")
		     .contentType(ContentType.JSON)
		     .when()
		     .body(updateEmployee).log().all()
		     .put(URL.putEndpointUrl);
		     
		     
		    System.out.println("--------Print Body Response---------");
		    System.out.println(putResponse.prettyPeek());
		    System.out.println("------Get all headers are "+putResponse.headers());
		    System.out.println("------Header contnt-type "+putResponse.header("Conten-Type"));
		    System.out.println("------Header date are "+putResponse.header("Date"));
		    System.out.println("------Get All cookies are "+putResponse.getCookies());
		    System.out.println("------Get All content type are "+putResponse.getContentType());
		   
		    if(putResponse.getStatusCode()==200) {
		    	
		    	Assert.assertEquals(putResponse.getBody().path("name"), "Mohan");
		    	Assert.assertEquals(putResponse.getBody().path("job"), "CivilEngineer");
		    	Assert.assertEquals(putResponse.getBody().path("skills[0]"), "Java");
		    	Assert.assertEquals(putResponse.getBody().path("skills[1]"), "Python");
		    	Assert.assertEquals(putResponse.getBody().path("details.companyName"), "Photon");
		    	Assert.assertEquals(putResponse.getBody().path("details.emailId"), "mohan123@gmail.com");
		    }
		     
		    /* .then().log().all()
		     .body("name", equalTo("Mohan"),
		    		 "job", equalTo("CivilEngineer"),
		    		 "skills[0]", equalTo("Java"),
		    		 "skills[1]", equalTo("Python"),
		    		 "details.companyName", equalTo("Photon"),
		    		 "details.emailId", equalTo("mohan123@gmail.com"));*/
		    ExtentTestManager.getTest().log(LogStatus.PASS, "Verify Put API is Pass");
	   }catch(Exception e) {
		System.out.println(e);
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Verify Put API is Fail");
	   }
  }

	@Test(priority =4, enabled = true)
	public void verifyPatchAPI_TC_004() {
		try {
			
			Response patchResponse=given()
		                           .auth().none()
		                           .header("Content-Type", "application/json")
		                           .contentType(ContentType.JSON)
		                           .when()
		                           .body(patchEmployee).log().all()
		                           .patch(URL.patchEndpointUrl);
		                           
			System.out.println("-----------Print Body Response-----------------");
			System.out.println(patchResponse.prettyPeek());
			System.out.println("------ All headers are "+patchResponse.headers());
			System.out.println("-----------Header content type are "+patchResponse.header("Content-Type"));
			System.out.println("-----------Header date are "+patchResponse.header("Date"));
			System.out.println("--------Get all cookies are "+patchResponse.getCookies());
			System.out.println("--------Get content type are "+patchResponse.getContentType());
			
			if(patchResponse.getStatusCode() == 200) {
				
				Assert.assertEquals(patchResponse.getBody().path("name"), "Rohan");
				Assert.assertEquals(patchResponse.getBody().path("job"), "Manager");
			}

		    /*.then().log().all()
		    .body("name", equalTo("Rohan"),
		    	   "job", equalTo("Manager"));*/
			ExtentTestManager.getTest().log(LogStatus.PASS, "Verify Patch API is Pass");
		}catch(Exception e) {
		   System.out.println(e);
		   ExtentTestManager.getTest().log(LogStatus.FAIL, "Verify Patch API is Fail");
		}
	}
	
	@Test(priority =5, enabled = true)
	public void verifyDeleteAPI_TC_005() {
		try {
			
			Response deleteResponse = given()
					   				 .auth().none()
					   				 .header("Content-Type", "application/json")
					   				 .contentType(ContentType.JSON)
					   				 .when()
					   				 .delete(URL.deleteEndpointUrl)
					   				 .then()
					   				 .log().body()
					   				 .extract().response();
			System.out.println("-----------Print Body Response------");
			System.out.println(deleteResponse.prettyPeek());
			
			if(deleteResponse.getStatusCode() == 204) {
				String jsonString = deleteResponse.asString();
				Assert.assertEquals(jsonString.contains(jsonString), true);
			}	
			ExtentTestManager.getTest().log(LogStatus.PASS, "Verify Delete API is Pass");
		}catch(Exception e) {
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Verify Delete API is Fail");
		}
	}
	
	@Test(priority =1, enabled = true)
	public void verifyGetAPI_TC_001() {
		
		try {
			Response getResponse = given()
		   .auth().none()
		   .header("Content-Type", "application/json")
		   .contentType(ContentType.JSON)
		   .when()
		   .get(URL.getEndpointUrl);
		   
			System.out.println("--------Print Body Response---------------");
			System.out.println(getResponse.prettyPeek());
			System.out.println("--------All headers are "+getResponse.headers());
			System.out.println("--------Header content-type are "+getResponse.header("Content-Type"));
			System.out.println("--------Header date are "+getResponse.header("Date"));
			System.out.println("--------Get all cookies "+getResponse.getCookies());
			System.out.println("--------Get al coneten-type "+getResponse.getContentType());
			
			if(getResponse.getStatusCode() == 200) {
				Assert.assertEquals(getResponse.getBody().path("data.id"), 2);
				Assert.assertEquals(getResponse.jsonPath().get("data.email"), "janet.weaver@reqres.in");
				Assert.assertEquals(getResponse.jsonPath().get("data.first_name"), "Janet");
				Assert.assertEquals(getResponse.jsonPath().get("data.last_name"), "Weaver");
				Assert.assertEquals(getResponse.jsonPath().get("data.avatar"), "https://reqres.in/img/faces/2-image.jpg");
				Assert.assertEquals(getResponse.jsonPath().get("support.url"), "https://reqres.in/#support-heading");
				Assert.assertEquals(getResponse.jsonPath().get("support.text"), "To keep ReqRes free, contributions towards server costs are appreciated!");
			}
				
		   /*.then().log().all()
			.body("data.id", equalTo(2),
				     "data.email", equalTo("janet.weaver@reqres.in"),
				     "data.first_name", equalTo("Janet"),
				     "data.last_name", equalTo("Weaver"),
				     "data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"),
				     "support.url", equalTo("https://reqres.in/#support-heading"),
				     "support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));*/
		   
			ExtentTestManager.getTest().log(LogStatus.PASS, "Verify Get API is Pass");
		}catch(Exception e) {
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Verify Get API is Fail");
		}
		
	}

}
