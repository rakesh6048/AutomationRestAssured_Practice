package com.api.testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class GraphQLScripts {
	
	@Test(enabled=true)
	public void verifyGraphQLQuery() {
		int characterId=14;
		String response = given().log().all().header("Content-Type", "application/json")
		                 .body("{\"query\":\"query($characterId : Int!, $episodeId : Int!)\\n{\\n\\n  character(characterId: $characterId)\\n {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n   location(locationId: 8)\\n  {\\n    name\\n    dimension\\n   }\\n    episode(episodeId : $episodeId)\\n   {\\n     name\\n     air_date\\n     episode\\n    }\\n}\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":1}}")
		                 .when().post("https://rahulshettyacademy.com/gq/graphql")
		                 .then().log().all().extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName, "Jenin");
		
	}
	
	@Test(enabled=true)
	public void verifyGraphQLMutation() {
		
		String response = given().log().all().header("Content-Type", "application/json")
		                 .body("{\"query\":\"mutation($locationName:String!, $characterName:String!,$episodeName: String!)\\n{\\n   createLocation(location :{name:$locationName, type:\\\"Southzone\\\", dimension:\\\"234\\\"}){\\n      id\\n    }\\n   \\n    createCharacter(character:{name:$characterName,type:\\\"Macho\\\",status:\\\"dead\\\",species:\\\"shetty\\\",gender:\\\"male\\\",image:\\\"png\\\",originId:12,locationId:14}){\\n     id\\n   }\\n\\n   createEpisode(episode : {name:$episodeName,air_date:\\\"1990 June\\\",episode:\\\"Netflix\\\"}){\\n    id\\n   }\\n\\n   deleteLocations(locationIds:[1481,1908]){\\n    locationsDeleted\\n   }\\n\\n}\",\"variables\":{\"locationName\":\"Newzland\",\"characterName\":\"Baskin Robin\",\"episodeName\":\"Manifest\"}}")
		                 .when().post("https://rahulshettyacademy.com/gq/graphql")
		                 .then().log().all().extract().response().asString();
		
		System.out.println(response);
		
	}
	

}
