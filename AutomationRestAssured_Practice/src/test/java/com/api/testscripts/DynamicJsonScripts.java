package com.api.testscripts;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.utils.Library;
import com.api.utils.PayLoad;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJsonScripts {
	
	
	@Test(dataProvider="data")
	public void addBook(String isbn, String aisle) {
		
		try {
			RestAssured.baseURI = "http://216.10.245.166";
			
			String addBookResponse =given().log().all().header("Content-Type", "application/json")
			                        .body(PayLoad.addBook(isbn, aisle))
			                        .when().post("Library/Addbook.php")
			                        .then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();
			
			System.out.println(addBookResponse);
			
			JsonPath jsp = new JsonPath(addBookResponse);
			
			String id = jsp.get("ID");
			System.out.println(id);
		
			
		}catch(Exception e) {
			System.out.println();
		}
	}
	
	@DataProvider(name="data")
	public Object[][] dataProvider() {
		
		Object obj[][]= {{"b3igoef", "3367"}, {"opsghoef","4567"}, {"gghdsh","87567"}};
		
		return obj;
		
	}

}
