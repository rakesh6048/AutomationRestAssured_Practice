package com.api.testscripts;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.getCoursesPojo.Api;
import com.api.getCoursesPojo.GetCourses;
import com.api.getCoursesPojo.Mobile;
import com.api.getCoursesPojo.WebAutomation;

import groovyjarjarantlr.collections.List;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;

public class OAuthTestScripts {
	
	
	@Test(enabled = true)
	public void  oAuthorization_Code_TestVerification() {
		try {
			
			String coursedTitle[] = {"Selenium Webdriver Java", "Cypress", "Protractor"};
			
		//Hit the url in browser and get the url as string
			/*WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			
			driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
			//driver.findElement(By.xpath("//div[contains(text(),'Use another account')]")).click();
			
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("rakesh.s6048@gmail.com");
			driver.findElement(By.xpath("(//span[contains(text(),'Next')]")).click();
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Photon@1993");
			
			driver.findElement(By.xpath("(//div[@class='VfPpkd-Jh9lGc'])[2]")).click();
			
			String url = driver.getCurrentUrl();*/
			
			String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXkUW9vsIscwWwZ5btuMoA6tspJ0mnuyqSUE1kcgzTUx03Jcpr0ufhls3-_yt0x7Tg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		    String partialCode = url.split("code=")[1];
		    String code = partialCode.split("&scope")[0];
		    System.out.println(code);
			
			
		//Get the access token and supply in next request to get response as all course price
		String tokenResponse= given().log().all().urlEncodingEnabled(false)
							 .queryParam("code", code)
		                     .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		                     .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		                     .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		                     .queryParam("grant_type", "authorization_code")
		                     .when()
		                     .post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(tokenResponse);
		
		JsonPath jsp = new JsonPath(tokenResponse);
		String accesstoken = jsp.get("access_token");
		System.out.println(accesstoken);
			
			
		//Get the actual response as all course price
		GetCourses gc= given().log().all().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON)
	    		 				  .when()
	    		 				  .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
			
			//start extract data from pojo class
		
			System.out.println("LinkedIn : "+gc.getLinkedIn());
			System.out.println("Instructor : "+gc.getInstructor());
			System.out.println(gc.getExpertise());
			System.out.println(gc.getUrl());
			System.out.println(gc.getServices());
			System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
			
			System.out.println("Get all details of API Corses");
			java.util.List<Api> apicourses = gc.getCourses().getApi();
			for(int i=0;i<apicourses.size();i++) {
				
				if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
					
					System.out.println("SoapUI Webservices Price : "+apicourses.get(i).getPrice());
				}
				System.out.println(apicourses.get(i).getCourseTitle());
				System.out.println(apicourses.get(i).getPrice());
				
			}
			System.out.println("\n"+"Get details of WebAutomation Corses");
			ArrayList<String> a = new ArrayList<String>();
			
			java.util.List<WebAutomation> webautomationCourses = gc.getCourses().getWebAutomation();
			for(int j=0;j<webautomationCourses.size();j++) {
				
				System.out.println(webautomationCourses.get(j).getCourseTitle());
				System.out.println(webautomationCourses.get(j).getPrice());
				a.add(webautomationCourses.get(j).getCourseTitle());
				
			}
			
			java.util.List<String> expectedList =Arrays.asList(coursedTitle);
			
			Assert.assertTrue(a.equals(expectedList));
			
			System.out.println("\n"+"Get details of Mobile Corses");
			java.util.List<Mobile> mobileCourses = gc.getCourses().getMobile();
			for(int k=0;k<mobileCourses.size();k++) {
				System.out.println(mobileCourses.get(k).getCourseTitle());
				System.out.println(mobileCourses.get(k).getPrice());
			}
		
		}catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	@Test(enabled = false)
	public void  oAuth_Client_Credential_TestVerification() {
		
		 try {
			 
		 //Get the access token and supply in next request to get response as all course price
			 
			String tokenResponse= given().log().all()
			        			 .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			        			 .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			        			 .queryParam("grant_type", "authorization_code")
			        			 .queryParam("scope", "https://www.googleapis.com/auth/userinfo.emai")
			        			 .when()
			        			 .post("https://www.googleapis.com/oauth2/v4/token").asString();
			
			System.out.println(tokenResponse);
			JsonPath jsp = new JsonPath(tokenResponse);
			String accesstoken = jsp.get("access_token");
			
			System.out.println(accesstoken);
			
			//Get all course price from actual response 
			String getResponse = given().log().all().queryParam("access_token", accesstoken)
			                     .when()
			                     .get("https://rahulshettyacademy.com/getCourse.php").asString();
				
			//start extract data from pojo class
			System.out.println(getResponse);
			
			 
		 }catch(Exception e) {
			 System.out.println(e);
	}
	}

}
