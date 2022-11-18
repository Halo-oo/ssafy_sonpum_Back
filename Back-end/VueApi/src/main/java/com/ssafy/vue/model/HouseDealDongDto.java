package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseDealInfoDto : 아파트 거래정보(시세)", description = "아파트의 거래 정보를 나타낸다.")
public class HouseDealDongDto {

	@ApiModelProperty(value = "동 코드")
	private String dongCode;
	@ApiModelProperty(value = "동 이름")
	private String dongName;
	
	public String getDongCode() {
		return dongCode;
	}
	
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	
	public String getDongName() {
		return dongName;
	}
	
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	@Override
	public String toString() {
		return "HouseDealDongDto [dongCode=" + dongCode + ", dongName=" + dongName + "]";
	}
	
}
