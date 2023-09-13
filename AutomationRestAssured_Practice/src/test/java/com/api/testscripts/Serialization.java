package com.api.testscripts;

import org.testng.annotations.Test;

import com.api.AddPlacePojo.AddPlace;
import com.api.AddPlacePojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class Serialization {
	
	@Test
	public void serializationTest() {
		
		try {
			AddPlace a1 = new AddPlace();
			a1.setName("Rakesh");
			a1.setAddress("29, side layout, cohen 09");
			a1.setPhone_number("(+91) 7305686048");
			a1.setLanguage("French-IN");
			a1.setWebsite("http://google.com");
			a1.setAccuracy("50");
			List<String> mylist = new ArrayList<String>();
			mylist.add("shoe park");
			mylist.add("shop");
			a1.setTypes(mylist);
			Location l =new Location();
			l.setLat(-38.383494);
			l.setLng(33.427362);
			a1.setLocation(l);
			
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick").header("Content-Type", "application/json")
		                 .body(a1)
		                 .when().post("/maps/api/place/add/json")
		                 .then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
		
		//create the pojo class for the response
		/*"{\r\n"
		+ "    \"status\": \"OK\",\r\n"
		+ "    \"place_id\": \"289d56df925bf5f402654d3bfe47016c\",\r\n"
		+ "    \"scope\": \"APP\",\r\n"
		+ "    \"reference\": \"1f7a1bca58f0da4a71d08a75e80fb4571f7a1bca58f0da4a71d08a75e80fb457\",\r\n"
		+ "    \"id\": \"1f7a1bca58f0da4a71d08a75e80fb457\"\r\n"
		+ "}"*/
		
			
		}catch(Exception e) {
			System.out.println();
		}
		
	}

}
