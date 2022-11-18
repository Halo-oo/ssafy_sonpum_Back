package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserTokenDto : 회원 토큰정보", description = "회원의 token에 관한 정보")
public class UserTokenDto {

	@ApiModelProperty(value = "회원 token")
	private String token;
	@ApiModelProperty(value = "회원 아이디")
	private String userId;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserTokenDto [token=" + token + ", userId=" + userId + "]";
	}

}
