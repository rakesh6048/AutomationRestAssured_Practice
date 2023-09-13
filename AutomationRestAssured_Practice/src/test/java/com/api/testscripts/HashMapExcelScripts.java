package com.api.testscripts;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.api.utils.DataDriven;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class HashMapExcelScripts {
	
	
	@Test
	public void verifyHashMapExcelTest() throws IOException {
		
		/*{
		    "name": "Appium Automation",
		    "isbn": "beedr",
		    "aisle": "299689",
		    "location" : {
		     
		        "lat" : -38.389456,
		        "lng" :  33.344273
		    
		    }
		    "author": "John foer"    
		}*/
		
		DataDriven d= new DataDriven();
		
		ArrayList data=d.getData("RestAddBook","RestAssured");
		
		//Simple Json implemented using HashMap
		HashMap<String, Object> addBookMap = new HashMap();
		
		addBookMap.put("name", data.get(1));
		addBookMap.put("isbn", data.get(2));
		addBookMap.put("aisle", data.get(3));
		addBookMap.put("author", data.get(4));
		
		//Nested Json implemented using HashMap
		/*HashMap<String, Object> inneraddBookMap = new HashMap();
		
		inneraddBookMap.put("lat", -38.389456);
		inneraddBookMap.put("lng", 33.344273);
		
		addBookMap.put("location", inneraddBookMap);*/
		
		
		
		RestAssured.baseURI="http://216.10.245.166";
		
		String resp = given().log().all().header("Content-Type", "application/json")
		       .body(addBookMap)
		       .when().post("/Library/Addbook.php")
		       .then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(resp);
		JsonPath js = new JsonPath(resp);
		String id = js.get("ID");
		System.out.println(id);
		
		// Delete Add Book Details
         HashMap<String, Object> deleteBookMap = new HashMap();
		
         deleteBookMap.put("ID", id);
		
		String deleteResp=given().log().all().header("Content-Type", "application/json")
		       .body(deleteBookMap)
		       .when().delete("/Library/DeleteBook.php")
		       .then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(deleteResp);
		
		JsonPath jsp = new JsonPath(deleteResp);
		String msg = jsp.getString("msg");
		System.out.println(msg);
		
	}
	

}
