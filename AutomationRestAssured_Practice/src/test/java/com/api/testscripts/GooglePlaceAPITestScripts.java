package com.api.testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.utils.Library;
import com.api.utils.PayLoad;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GooglePlaceAPITestScripts {
	
	@Test(enabled = false)
	public void verifyGooglePlaceAPI() {
	  
		JsonPath jsp;
		try {
			
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add the Place and validate
		
		String postResponse = given().log().all().queryParam("key", "qaclick").header("Content-Type", "application/json")
		                 .body(PayLoad.addPlace())
		                 .when().post("/maps/api/place/add/json")
		                 .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		                 .extract().response().asString();
		System.out.println(postResponse);
		
		jsp = new JsonPath(postResponse);
		
		String placeID = jsp.getString("place_id");
	    System.out.println(placeID);
	    
	    //update the place and validate
	   String newAddress = "New Road Asteriliya";
	   
	   String updateResponse = given().log().all().queryParam("key", "qaclick123").queryParam("placeID", placeID).header("Content-Type", "application/json")
	                         .body("{\r\n"
	                        + "\"place_id\":\""+placeID+"\",\r\n"
	                        + "\"address\":\""+newAddress+"\",\r\n"
	                        + "\"key\":\"qaclick123\"\r\n"
	                        + "}")
	                         .when().put("/maps/api/place/update/json")
	                         .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
	   
	   System.out.println(updateResponse);
	   
	   jsp = new JsonPath(updateResponse);
	   
	   String successMsg =jsp.getString("msg");
	   
	   System.out.println(successMsg);
	   
	   Assert.assertEquals(successMsg, "Address successfully updated");
	    
	    //Get the place and validate
	   
	   String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("placeID", placeID)
	                       .when().get("/maps/api/place/get/json")
	                       .then().log().all().assertThat().statusCode(200).body("name", equalTo("Raj Singh")).extract().response().asString();
			
	    System.out.println(getResponse);
	    
	    jsp = new JsonPath(getResponse);
	    
	    String actualAddress = jsp.getString("address");
	    
	    System.out.println(actualAddress);
	    
	    Assert.assertEquals(actualAddress, newAddress);
	    
	    //Delete the place and validate
	    
	    String deleteResponse = given().log().all().queryParam("key", "qaclick").header("Content-Type", "application/json")
	    					   .body("{\r\n"
	    					   + "    \"place_id\":\""+placeID+"\"\r\n"
	           		           + "}")
	             .when().post("/maps/api/place/delete/json")
	             .then().log().all().assertThat().statusCode(200).body("status", equalTo("OK")).extract().response().asString();
	    
	    System.out.println(deleteResponse);
	    
	    jsp = new JsonPath(deleteResponse);
	    
	    String statusMsg = jsp.getString("status");
	    
	    System.out.println(statusMsg);
	    
	    Assert.assertEquals(statusMsg, "ok");
	    
		}catch(Exception e) {
			
		}
		
		
	}
	
	@Test(enabled = true)
	public void verifyGoogleAPI() {
		
		JsonPath jsp;
		
		try {
			
			RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add Place and validate the response data
		String postResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			                 .body(PayLoad.addPlace())
			                 .when().post("/maps/api/place/add/json")
			                 .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"), "status" , equalTo("OK"))
			                 .extract().response().asString();
		System.out.println(postResponse);
		jsp = new JsonPath(postResponse);
		
		String place_id = jsp.getString("place_id");
		System.out.println(place_id);
		
		//Update Place and validate the response
		String newAddress = "New Road, USA";
		String putResponse =given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type", "application/json")
		                    .body("{\r\n"
		       		        + "\"place_id\":\""+place_id+"\",\r\n"
		       		        + "\"address\":\""+newAddress+"\",\r\n"
		       		        + "\"key\":\"qaclick123\"\r\n"
		       		        + "}")
		       .when().put("/maps/api/place/update/json")
		       .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		System.out.println(putResponse);
		
		jsp = new JsonPath(putResponse);
		
		String statusMsg = jsp.getString("msg");
		System.out.println(statusMsg);
		
		Assert.assertEquals(statusMsg, "Address successfully updated");
		
		//Get Place and validate the response data
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
							.when().get("/maps/api/place/get/json")
							.then().log().all().assertThat().statusCode(200).body("name", equalTo("Raj Singh"), "address", equalTo(newAddress)).extract().response().asString();
			
		System.out.println(getResponse);
		jsp = new JsonPath(getResponse);
		
		String name = jsp.getString("name");
		String actualAddress = jsp.getString("address");
		System.out.println(name);
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
		//Delete place and validate the response data
		String deleteResponse = given().log().all().queryParam("key", "qaclick123")
		                       .body("{\r\n"
		                       + "    \"place_id\":\""+place_id+"\"\r\n"
		                       + "}")
		       .when().delete("/maps/api/place/delete/json")
		       .then().log().all().assertThat().statusCode(200).body("status", equalTo("OK")).extract().response().asString();
		
		System.out.println(deleteResponse);
		jsp = new Library().rawToJson(deleteResponse);
		
		String status = jsp.getString("status");
		
		System.out.println(status);
		
		Assert.assertEquals(status, "OK");
		
		
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
