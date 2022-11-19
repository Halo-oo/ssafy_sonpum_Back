package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseProductBookmarkDto : 매물 북마크", description = "회원이 북마크 한 아파트 매물 정보를 나타낸다.")
public class HouseProductBookmarkDto {

	@ApiModelProperty(value = "북마크 번호")
	private int houseProductBookmarkId;
	@ApiModelProperty(value = "회원 ID")
	private String userId;
	@ApiModelProperty(value = "매물 번호")
	private int houseProductId;
	
	public int getHouseProductBookmarkId() {
		return houseProductBookmarkId;
	}
	
	public void setHouseProductBookmarkId(int houseProductBookmarkId) {
		this.houseProductBookmarkId = houseProductBookmarkId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getHouseProductId() {
		return houseProductId;
	}
	
	public void setHouseProductId(int houseProductId) {
		this.houseProductId = houseProductId;
	}

	@Override
	public String toString() {
		return "HouseProductBookmarkDto [houseProductBookmarkId=" + houseProductBookmarkId + ", userId=" + userId
				+ ", houseProductId=" + houseProductId + "]";
	}

}
