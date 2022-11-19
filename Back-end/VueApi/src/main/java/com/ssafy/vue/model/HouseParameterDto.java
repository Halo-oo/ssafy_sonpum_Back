package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseParameterDto : (시세)아파트 거래정보 검색", description = "아파트의 거래 정보 검색에 필요한 정보")
public class HouseParameterDto {

	@ApiModelProperty(value = "동 코드")
	private String dongCode;
	@ApiModelProperty(value = "검색 조건")
	private String key;
	@ApiModelProperty(value = "검색어")
	private String word;
	@ApiModelProperty(value = "거래 년도")
	private String dealYear;
	@ApiModelProperty(value = "거래 월")
	private String dealMonth;
	
	public String getDongCode() {
		return dongCode;
	}
	
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getDealYear() {
		return dealYear;
	}
	
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	
	public String getDealMonth() {
		return dealMonth;
	}
	
	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}

	@Override
	public String toString() {
		return "HouseParameterDto [dongCode=" + dongCode + ", key=" + key + ", word=" + word + ", dealYear=" + dealYear
				+ ", dealMonth=" + dealMonth + "]";
	}
	
}
