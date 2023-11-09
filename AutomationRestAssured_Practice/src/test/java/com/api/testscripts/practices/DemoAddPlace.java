package com.api.testscripts.practices;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.utils.Payloads;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DemoAddPlace {

	@Test
	public void verifyAddPlace() {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		try {
	//Add Place
			String addPlace=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			       .body(Payloads.addPlace())
			       .when().post("/maps/api/place/add/json")
			       .then().log().all().assertThat().statusCode(200).extract().response().asString();
			
			System.out.println(addPlace);
			
			JsonPath js = new JsonPath(addPlace);
			
			String PlaceId = js.getString("place_id");
			System.out.println(PlaceId);
  //Update Place
	       String New_Place="New City Road, America";
	 String updatePlace = given().log().all().queryParam("key", "qaclick123").queryParam("PlaceId", PlaceId).header("Content-Type", "application/json")
	                   .body("{\r\n"
	             		+ "\"place_id\":\""+PlaceId+"\",\r\n"
	             		+ "\"address\":\""+New_Place+"\",\r\n"
	             		+ "\"key\":\"qaclick123\"\r\n"
	             		+ "}\r\n"
	             		+ "")
	             .when().put("/maps/api/place/update/json")
	             .then().log().all().assertThat().statusCode(200).extract().response().asString();
	 System.out.println(updatePlace);
	 
	 JsonPath js1 = new JsonPath(updatePlace);
	 String msg = js1.getString("msg");
	 Assert.assertEquals(msg, "Address successfully updated");
	 
	 //Get Place
	 
	   /* String getresponse=given().log().all().queryParam("key", "qaclick123").queryParam("PlaceId", PlaceId)
	           .when().get("/maps/api/place/get/json")
	           .then().log().all().assertThat().statusCode(200).extract().response().toString();*/
	 
	 String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("placeID", PlaceId)
             .when().get("/maps/api/place/get/json")
             .then().log().all().assertThat().statusCode(200).extract().response().asString();
	    
	    
		System.out.println(getResponse);
		
		JsonPath jsp = new JsonPath(getResponse);
		
		String name = jsp.getString("name");
		
		System.out.println(name);
		
		//Assert.assertEquals(address, "New City Road, America");
	    
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
