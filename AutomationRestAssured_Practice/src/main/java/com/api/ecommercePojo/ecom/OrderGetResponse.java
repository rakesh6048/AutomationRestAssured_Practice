package com.api.ecommercePojo.ecom;

import java.util.List;

import com.api.pojo.getresponse.Data;

public class OrderGetResponse {

	private DataDetails data;
	private String message;
	
	public DataDetails getData() {
		return data;
	}
	public void setData(DataDetails data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
