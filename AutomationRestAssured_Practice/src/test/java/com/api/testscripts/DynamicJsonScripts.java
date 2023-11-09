package com.api.testscripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.utils.Library;
import com.api.utils.Payloads;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJsonScripts {
	
	@Test(dataProvider="data")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
	   String response1 = given().log().all().header("Content-Type", "application/json")
	                     .body(Payloads.Addbook(isbn, aisle))
	                     .when().post("/Library/Addbook.php")
	                     .then().log().all().assertThat().statusCode(200)
	                     .extract().response().asString();
	   
	   JsonPath js = Library.rawToJson(response1);
	   String id = js.get("ID");
	   System.out.println("ID : "+id);	    
	}
	
	@DataProvider(name="data")
	public Object[][] getData() {
		
		Object obj[][] ={{"absd","2444"},{"fghs","5446"},{"akjhf","7656"}};
		
		return obj;
	}
}
