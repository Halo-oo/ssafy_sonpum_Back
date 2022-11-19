package com.ssafy.vue.model;

import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseProductDto : 아파트 매물정보", description = "아파트 매물 정보를 나타낸다.")
public class HouseProductDto {

	@ApiModelProperty(value = "매물 ID")
	private int houseProductId;
	@ApiModelProperty(value = "기업회원 ID")
	private String userId;
	@ApiModelProperty(value = "주소 ID(addressId = apartCode)")
	private BigInteger addressId;
	@ApiModelProperty(value = "층")
	private String floor;
	@ApiModelProperty(value = "건축 년도")
	private String buildYear;
	@ApiModelProperty(value = "매매 가격")
	private String dealAmount;
	@ApiModelProperty(value = "평수")
	private String area;
	@ApiModelProperty(value = "거래 유형(월/전세, 매매)")
	private String dealType;
	@ApiModelProperty(value = "판매여부")
	private String stateFlag;
	@ApiModelProperty(value = "내용(판매자 말)")
	private String content;
	
	public int getHouseProductId() {
		return houseProductId;
	}
	
	public void setHouseProductId(int houseProductId) {
		this.houseProductId = houseProductId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public BigInteger getAddressId() {
		return addressId;
	}
	
	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}
	
	public String getFloor() {
		return floor;
	}
	
	public void setFloor(String floor) {
		this.floor = floor;
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
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getDealType() {
		return dealType;
	}
	
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	
	public String getStateFlag() {
		return stateFlag;
	}
	
	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "HouseProductDto [houseProductId=" + houseProductId + ", userId=" + userId + ", addressId=" + addressId
				+ ", floor=" + floor + ", buildYear=" + buildYear + ", dealAmount=" + dealAmount + ", area=" + area
				+ ", dealType=" + dealType + ", stateFlag=" + stateFlag + ", content=" + content + "]";
	}
	
}
