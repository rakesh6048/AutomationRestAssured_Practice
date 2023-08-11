package com.api.testscripts;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import com.api.pojo.addbook.AddBook;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PojoTestScripts {
	
	
	@Test
	public void addBook(){
		
		try {
			AddBook bookDetails = new AddBook("Learn Appium Automation with Java","bklk","298k","John foe");
			
			RestAssured.baseURI="http://216.10.245.166";
			
			String addBookResponse = given().log().all().header("Content-Type","application/json")
			                         .body(bookDetails)
			                         .when().post("Library/Addbook.php")
			                         .then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println(addBookResponse);
			
			JsonPath jsp = new JsonPath(addBookResponse);
			
			String statusMsg = jsp.get("Msg");
			System.out.println(statusMsg);
			String id = jsp.get("ID");
			System.out.println(id);
			
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
}
