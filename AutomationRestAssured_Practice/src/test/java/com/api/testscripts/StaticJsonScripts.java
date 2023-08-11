package com.api.testscripts;

import org.testng.annotations.Test;

import com.api.utils.Library;
import com.api.utils.PayLoad;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJsonScripts {
	
	@Test(enabled = false)
	public void addBook() {
		
		try {
			
		RestAssured.baseURI = "http://216.10.245.166";
		
		String addBookResponse =given().log().all().header("Content-Type", "application/json")
								.body(new String(Files.readAllBytes(Paths.get("D:\\API Automation\\API Autmation Using Rest Assured\\JSON PayLoad Files\\AddBookLatest.json"))))
								.when().post("Library/Addbook.php")
								.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();
		System.out.println(addBookResponse);
		
		JsonPath jsp = new Library().rawToJson(addBookResponse);
		
		String id = jsp.get("ID");
		
		System.out.println(id);
			
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
 
	}
	
	@Test(enabled = true)
	public void addPlace() {
		
		try {
			RestAssured.baseURI="https://rahulshettyacademy.com";
			
			//Add the Place and validate
			
			String postResponse = given().log().all().queryParam("key", "qaclick").header("Content-Type", "application/json")
			                 .body(new String(Files.readAllBytes(Paths.get("D:\\API Automation\\API Autmation Using Rest Assured\\JSON PayLoad Files\\ApddPlaceLatest.json"))))
			                 .when().post("/maps/api/place/add/json")
			                 .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
			                 .extract().response().asString();
			System.out.println(postResponse);
			
			JsonPath jsp = new JsonPath(postResponse);
			
			String placeID = jsp.getString("place_id");
		    System.out.println(placeID);
			
			
		}catch(Exception e) {
			
		}
	}
}
