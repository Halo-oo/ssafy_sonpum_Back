package com.ssafy.vue.model;

import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseDealInfoDto : 아파트 거래정보(시세)", description = "아파트의 거래 정보를 나타낸다.")
public class HouseDealInfoDto {

	@ApiModelProperty(value = "아파트 일련번호")
	private BigInteger addressId;
	@ApiModelProperty(value = "아파트 이름")
	private String apartName;
	@ApiModelProperty(value = "동 코드")
	private String dongCode;
	@ApiModelProperty(value = "건축 년도")
	private String buildYear;
	@ApiModelProperty(value = "매매 가격")
	private String dealAmount;
	@ApiModelProperty(value = "거래 년도")
	private String dealYear;
	@ApiModelProperty(value = "거래 월")
	private int dealMonth;
	@ApiModelProperty(value = "거래 일")
	private String dealDay;
	@ApiModelProperty(value = "면적")
	private String area;
	@ApiModelProperty(value = "층")
	private String floor;
	@ApiModelProperty(value = "Lat")
	private String lat;
	@ApiModelProperty(value = "Lng")
	private String lng;
	
	public BigInteger getAddressId() {
		return addressId;
	}
	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}
	public String getApartName() {
		return apartName;
	}
	public void setApartName(String apartName) {
		this.apartName = apartName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	public int getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealDay() {
		return dealDay;
	}
	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "HouseDealInfoDto [addressId=" + addressId + ", apartName=" + apartName + ", dongCode=" + dongCode
				+ ", buildYear=" + buildYear + ", dealAmount=" + dealAmount + ", dealYear=" + dealYear + ", dealMonth="
				+ dealMonth + ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor + ", lat=" + lat + ", lng="
				+ lng + "]";
	}
}
