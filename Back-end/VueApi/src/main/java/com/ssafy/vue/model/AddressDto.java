package com.ssafy.vue.model;

import java.math.BigInteger;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AddressDto {

	@ApiModelProperty(value = "주소 ID(addressId = apartCode)")
	private BigInteger addressId;
	@ApiModelProperty(value = "동 코드")
	private String dongCode;
	@ApiModelProperty(value = "도로명")
	private String roadName;
	@ApiModelProperty(value = "건물 본 번호")
	private String roadNameBonbun;
	@ApiModelProperty(value = "건물 부 번호")
	private String roadNameBubun;
	@ApiModelProperty(value = "아파트 이름")
	private String apartName;
	@ApiModelProperty(value = "건축 년도")
	private String buildYear;
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
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getRoadNameBonbun() {
		return roadNameBonbun;
	}
	public void setRoadNameBonbun(String roadNameBonbun) {
		this.roadNameBonbun = roadNameBonbun;
	}
	public String getRoadNameBubun() {
		return roadNameBubun;
	}
	public void setRoadNameBubun(String roadNameBubun) {
		this.roadNameBubun = roadNameBubun;
	}
	public String getApartName() {
		return apartName;
	}
	public void setApartName(String apartName) {
		this.apartName = apartName;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
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
		return "AddressDto [addressId=" + addressId + ", dongCode=" + dongCode + ", roadName=" + roadName
				+ ", roadNameBonbun=" + roadNameBonbun + ", roadNameBubun=" + roadNameBubun + ", apartName=" + apartName
				+ ", buildYear=" + buildYear + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
	
}
