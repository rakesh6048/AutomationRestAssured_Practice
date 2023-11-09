package com.api.ecommercePojo.ecom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDetails {

	private String _id;
	private String orderById;
	private String orderBy;
	private String productOrderedId;
	private String productName;
	private String country;
	private String productDescription;
	private String productImage;
	private String orderPrice;
	private Integer _v;
	
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getOrderById() {
		return orderById;
	}
	public void setOrderById(String orderById) {
		this.orderById = orderById;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getProductOrderedId() {
		return productOrderedId;
	}
	public void setProductOrderedId(String productOrderedId) {
		this.productOrderedId = productOrderedId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Integer getV() {
		return _v;
	}
	public void setV(Integer _v) {
		this._v = _v;
	}
	

}
