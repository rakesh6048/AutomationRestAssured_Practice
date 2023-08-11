package com.api.utils;

import io.restassured.path.json.JsonPath;

public class Library {
	
	public static JsonPath  rawToJson(String response) {
		
		JsonPath jsp = new JsonPath(response);
		return jsp;
	}

}
