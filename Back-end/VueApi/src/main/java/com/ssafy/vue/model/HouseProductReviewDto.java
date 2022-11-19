package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseProductReviewDto : 매물 리뷰", description = "회원이 아파트 매물에 대해 작성한 리뷰를 나타낸다.")
public class HouseProductReviewDto {

	@ApiModelProperty(value = "리뷰 번호")
	private int houseProductReviewId;
	@ApiModelProperty(value = "매물 번호")
	private int houseProductId;
	@ApiModelProperty(value = "회원 ID (매물을 등록한 회원)")
	private String userId;
	@ApiModelProperty(value = "작성자 ID (리뷰를 작성한 회원)")
	private String writerUserId;
	@ApiModelProperty(value = "별점")
	private int rating;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "이미지")
	private String image;
	@ApiModelProperty(value = "등록 시간")
	private String regtime;
	
	public int getHouseProductReviewId() {
		return houseProductReviewId;
	}
	
	public void setHouseProductReviewId(int houseProductReviewId) {
		this.houseProductReviewId = houseProductReviewId;
	}
	
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
	
	public String getWriterUserId() {
		return writerUserId;
	}
	
	public void setWriterUserId(String writerUserId) {
		this.writerUserId = writerUserId;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getRegtime() {
		return regtime;
	}
	
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	
	@Override
	public String toString() {
		return "HouseProductReviewDto [houseProductReviewId=" + houseProductReviewId + ", houseProductId="
				+ houseProductId + ", userId=" + userId + ", writerUserId=" + writerUserId + ", rating=" + rating + ", content="
				+ content + ", image=" + image + ", regtime=" + regtime + "]";
	}

}
