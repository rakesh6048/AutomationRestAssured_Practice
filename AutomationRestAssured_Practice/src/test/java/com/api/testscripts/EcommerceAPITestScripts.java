package com.api.testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.ecommercePojo.LoginRequest;
import com.api.ecommercePojo.LoginResponse;
import com.api.ecommercePojo.Order;
import com.api.ecommercePojo.OrderDetails;
import com.api.ecommercePojo.ecom.AddProductResponse;
import com.api.ecommercePojo.ecom.DataDetails;
import com.api.ecommercePojo.ecom.OrderGetResponse;
import com.api.ecommercePojo.ecom.OrdersResponse;
import com.api.pojo.getresponse.Data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcommerceAPITestScripts {
	
	@Test
	public void ecommerceAPITest() {
		
		
		
		RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest login = new LoginRequest();
		login.setUserEmail("rahulshetty@gmail.com");
		login.setUserPassword("Iamking@000");
		
		RequestSpecification reqLogin = given().log().all().spec(res).body(login);
		
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
				                 .then().log().all().extract().response().as(LoginResponse.class);
		
		String token =loginResponse.getToken();
		String userId = loginResponse.getUserId();
		System.out.println(token);
		System.out.println(userId);
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).build();
		
		
		RequestSpecification addProductReq = given().log().all().spec(addProductBaseReq).param("productName", "Laptop").param("productAddedBy", userId)
												   .param("productCategory", "fashion").param("productSubCategory", "shirts")
												   .param("productPrice", "11500").param("productDescription", "Lenevo")
												   .param("productFor", "women")
												   .multiPart("productImage", new File("D://API Automation//API Autmation Using Rest Assured//Section 9 , 10- Oauth 2.0//ClientCredential.png"));
		
		AddProductResponse addProductResponse = addProductReq.when().post("/api/ecom/product/add-product")
				                                 .then().log().all().extract().response().as(AddProductResponse.class);
		
		//JsonPath jsp = new JsonPath(addProductResponse);
		//String productId = jsp.get("productId");
		String productId = addProductResponse.getProductId();
		System.out.println(productId);
		
		//Creater Order
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		
		orderDetailList.add(orderDetail);
		
		Order orders = new Order();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);
		
		OrdersResponse AddOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order")
				                      .then().log().all().extract().response().as(OrdersResponse.class);
				              					                        
				                     // OrdersResponse	                      //.as(OrdersResponse.class);
		
		//System.out.println(AddOrderResponse);
		
		//JsonPath jsp3 = new JsonPath(AddOrderResponse);
		
		//String orderId =jsp3.get("orders[0]");
		
		//System.out.println("orderId : "+orderId);
		
		List<String> orderIds =AddOrderResponse.getOrders();
		
		String	orderId1="";
		for(String ordId : orderIds) {
		orderId1 =ordId;
			 
		}
		String orderId =orderId1;
		System.out.println("Second Order ID : "+orderId);
		
		//System.out.println(orderId[0]);
		
		//View Order Details
		RequestSpecification viewOrderDetailsBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("id", orderId).addHeader("authorization", token).build();
		
		//System.out.println("Third Order ID : "+orderId);
		
		RequestSpecification viewOrderDetailsReq = given().log().all().spec(viewOrderDetailsBaseReq);
		
		OrderGetResponse viewOrderDetailsResponse = viewOrderDetailsReq.when().get("/api/ecom/order/get-orders-details")
				                          .then().log().all().extract().response().as(OrderGetResponse.class);
		
		String message =viewOrderDetailsResponse.getMessage();
		System.out.println(message);
		
		DataDetails getAllData =viewOrderDetailsResponse.getData();
		
        String productName = getAllData.getProductName();
        String country = getAllData.getCountry();
        String productDescription = getAllData.getProductDescription();
        
        Assert.assertEquals(productName, "Laptop");
		Assert.assertEquals(country, "India");
		Assert.assertEquals(productDescription, "Lenevo");
		
		
		/*System.out.println(viewOrderDetailsResponse);
		
		JsonPath jsp4 = new JsonPath(viewOrderDetailsResponse);
		
		String message = jsp4.get("message");
		System.out.println(message);
		
		String productName = jsp4.get("data.productName");
		String country = jsp4.get("data.country");
		String productDescription = jsp4.get("data.productDescription");
		
		System.out.println(productName);
		System.out.println(country);
		System.out.println(productDescription);
		
		Assert.assertEquals(productName, "Laptop");
		Assert.assertEquals(country, "India");
		Assert.assertEquals(productDescription, "Lenevo");*/
		
		//Delete Order
		
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);
		
		String deleteProductResponse = deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}")
				                     .then().log().all().extract().response().asString();
		
		JsonPath jsp1 = new JsonPath(deleteProductResponse);
		
		Assert.assertEquals("Product Deleted Successfully", jsp1.get("message"));
		
		//Delete Order
		
		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteOrderReq = given().log().all().spec(deleteOrderBaseReq).pathParam("orderId", orderId);
		
		String deleteOrderResponse = deleteOrderReq.when().delete("/api/ecom/order/delete-order/{orderId}")		
				                     .then().log().all().extract().response().asString();
		
		JsonPath jsp2 =new JsonPath(deleteOrderResponse);
		
		Assert.assertEquals("Orders Deleted Successfully", jsp2.get("message"));
	}

}
