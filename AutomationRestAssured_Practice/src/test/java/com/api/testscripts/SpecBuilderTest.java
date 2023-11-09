package com.api.testscripts;

import org.testng.annotations.Test;

import com.api.AddPlacePojo.AddPlace;
import com.api.AddPlacePojo.AddPlaceResponse;
import com.api.AddPlacePojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {
	
	@Test(enabled=false)
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
		
   RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick").setContentType(ContentType.JSON).build();
		
   ResponseSpecification resSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
		RequestSpecification res = given().log().all().spec(req)
		                 .body(a1);
		                 
		Response response= res.when().post("/maps/api/place/add/json")
		                 .then().spec(resSpec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
					
		}catch(Exception e) {
			System.out.println();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		}
	
	@Test(enabled=true)
	public void verifiedAddPlace() {
		
		AddPlace add = new AddPlace();
	    add.setName("Rakesh");
	    add.setAddress("Cross City, US");
	    add.setLanguage("English");
	    add.setPhone_number("6736237873");
	    add.setWebsite("www.gmail.com");
	    add.setAccuracy("142");
	    List<String> mylist = new ArrayList<String>();
	    mylist.add("shoe park");
	    mylist.add("shop");
	    add.setTypes(mylist);
	    
	    Location l= new Location();
	    l.setLat(-38.383494);
	    l.setLng(33.427362);
	    add.setLocation(l);
	    
	    RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick").setContentType(ContentType.JSON).build();
	    
	    ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    
	    RequestSpecification res = given().log().all().spec(req).body(add);
	    
	    AddPlaceResponse response = res.when().post("/maps/api/place/add/json")
	    		            .then().log().all().spec(resSpec).extract().response().as(AddPlaceResponse.class);
	    //System.out.println(response.asString());
	    
	    //String responses = response.asString();
	    
	    //JsonPath js = new JsonPath(responses);
	    
	   // String place_id = js.get("place_id");
	   // System.out.println(place_id);
	    
	    System.out.println(response.getPlace_id());
	}

}
