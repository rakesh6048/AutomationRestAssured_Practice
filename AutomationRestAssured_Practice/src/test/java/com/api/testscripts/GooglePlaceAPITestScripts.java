package com.api.testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.utils.Library;
import com.api.utils.Payloads;

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
		                 .body(Payloads.addPlace())
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
	                       .then().log().all().assertThat().statusCode(200).body("name", equalTo("Rajputana House")).extract().response().asString();
			
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
	             .when().delete("/maps/api/place/delete/json")
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
		    
			String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			       .body(Payloads.addPlace())
			       .when().post("/maps/api/place/add/json")
			       .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();
			
			System.out.println(response);
		    JsonPath jsps = new JsonPath(response);
		    String place_id = jsps.get("place_id");
		    System.out.println(place_id);
		    
		  //Add Place 
		    
		    String new_address="All New Holland";
		    
		    String updateResponse =given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type", "application/json")
		           .body("{\r\n"
		           		+ "\"place_id\":\""+place_id+"\",\r\n"
		           		+ "\"address\":\""+new_address+"\",\r\n"
		           		+ "\"key\":\"qaclick123\"\r\n"
		           		+ "}")
		           .when().put("/maps/api/place/update/json")
		           .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		    System.out.println(updateResponse);
		    JsonPath jsps1= new JsonPath(updateResponse);
		    String msg=jsps1.getString("msg");
		    System.out.println(msg);
		    
		    //Get Place
		    
		   String getResponse= given().log().all().queryParam("key", "qaclick").queryParam("place_id", place_id)
		            .when().get("/maps/api/place/get/json")
		            .then().log().all().extract().response().asString();
		   System.out.println(getResponse); 
		   //JsonPath js = new JsonPath(getResponse);
		   //String name = js.get("name");
		   //String address = js.get("address");
		   //System.out.println(name);
		   //System.out.println(address);
		   
		   //Assert.assertEquals(new_address, address);
		   
		   // Delate Palce
		   
		   String deleteResponse = given().log().all().queryParam("key", "qaclick").header("ContentType", "application/json")
		           .body("{\r\n"
		           		+ "\"place_id\":\""+place_id+"\"\r\n"
		           		+ "}")
		           .when().delete("/maps/api/place/delete/json")
		           .then().log().all().assertThat().statusCode(200).extract().response().asString();
		   System.out.println(deleteResponse);
		   
		   JsonPath js1= new JsonPath(deleteResponse);
		   String status = js1.get("status");
		   System.out.println(status);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
