package com.api.testscripts.practices;


import org.jsoup.Connection.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.ecommercePojo.Order;
import com.api.ecommercePojo.ecom.LoginRequest;
import com.api.ecommercePojo.ecom.LoginResponse;
import com.api.ecommercePojo.ecom.OrderDetails;
import com.api.ecommercePojo.ecom.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class EcomerceTestScripts {

	@Test
	public void verifyEcomerceProduct() {
		
		//Login to Ecomerce Site
		
		LoginRequest l=new LoginRequest();
		l.setUserEmail("rakesh.s6048@gmail.com");
		l.setUserPassword("Photon@1993");
		
		RequestSpecification reqLoginSpec= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		ResponseSpecification resLoginSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification loginReq = given().spec(reqLoginSpec).body(l);
		
		LoginResponse res= loginReq.when().post("/api/ecom/auth/login")
				     .then().log().all().spec(resLoginSpec).extract().response().as(LoginResponse.class);
		
		//.then().log().all().spec(resLoginSpec).extract().response().asString();
		
		//JsonPath jsp = new JsonPath(res);
		
		//String token = jsp.get("token");
		String token = res.getToken();
		System.out.println("token : "+token);
		
		//String userId = jsp.get("userId");
		String userId =res.getUserId();
		System.out.println("UserID : "+userId);
		
		//Create Product
		
		RequestSpecification creatPReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		
		
		ResponseSpecification createPRes = new ResponseSpecBuilder().expectStatusCode(201).build();
		
		RequestSpecification productReq = given().spec(creatPReq).param("productName", "Laptop").param("productAddedBy", userId)
																 .param("productCategory", "fashion").param("productSubCategory", "electronics")
																 .param("productPrice", "11500").param("productDescription", "Lenevo")
																 .param("productFor", "men")
																 .multiPart("productImage", new File("D:\\API Automation\\API Autmation Using Rest Assured\\Section 11 Deserialization\\Pojo_Hiharariki_2.png"));
		String productRes =productReq.when().post("/api/ecom/product/add-product")
				            .then().log().all().spec(createPRes).extract().response().asString();
		System.out.println(productRes);
		JsonPath js1=new JsonPath(productRes);
		
		String productId = js1.get("productId");
		System.out.println("Product ID : "+productId);
		
		//Create Order
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification orderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		
	//ResponseSpecification orderRes = new ResponseSpecBuilder().expectStatusCode(201).build();
		
		RequestSpecification creatOrerReq = given().spec(orderReq).body(orders);
		
		String orderResponse = creatOrerReq.when().post("/api/ecom/order/create-order")
				               .then().log().all().extract().response().asString();
		
		System.out.println(orderResponse);
		
		JsonPath js2= new JsonPath(orderResponse);
		
		String orderId = js2.get("orders[0]");
		System.out.println("Order ID "+orderId);
		
    // Get Order Details
		
		RequestSpecification getOrdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("id", orderId).addHeader("authorization", token).build();
		
		RequestSpecification getOrderReq = given().log().all().spec(getOrdReq);
	    
		String response = getOrderReq.when().get("/api/ecom/order/get-orders-details")
				          .then().log().all().extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js3 = new JsonPath(response);
		
		String productName = js3.get("data.productName");
		
		Assert.assertEquals(productName, "Laptop");
		
	//Delete Order
		RequestSpecification deleteOrdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteOrder = given().log().all().spec(deleteOrdReq).pathParam("orderId", orderId);
		
		String delteResponse = deleteOrder.when().delete("/api/ecom/order/delete-order/{orderId}")
				               .then().log().all().extract().response().asString();
		
		System.out.println(delteResponse);
		
	//Delete Product
		
        RequestSpecification deleteProdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProduct = given().log().all().spec(deleteProdReq).pathParam("productId", productId);
		
		String delteProdResponse = deleteProduct.when().delete("/api/ecom/product/delete-product/{productId}")
				               .then().log().all().extract().response().asString();
		
		System.out.println(delteProdResponse);
				          
	}
}
