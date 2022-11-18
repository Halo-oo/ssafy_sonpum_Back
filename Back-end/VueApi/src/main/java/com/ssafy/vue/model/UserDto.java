package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {

	@ApiModelProperty(value = "회원 아이디")
	private String userId;
	@ApiModelProperty(value = "회원 이름")
	private String userName;
	@ApiModelProperty(value = "회원 비밀번호")
	private String userPwd;
	@ApiModelProperty(value = "회원 이메일")
	private String email;
	@ApiModelProperty(value = "회원 가입일")
	private String joindate;
	@ApiModelProperty(value = "역할(권한)")
	private String role;
	@ApiModelProperty(value = "탈퇴 여부")
	private int delFlag;
	@ApiModelProperty(value = "회원 핸드폰 번호")
	private String phoneNumber;
	@ApiModelProperty(value = "신고 count")
	private int reportCount; 
	@ApiModelProperty(value = "회원 프로필 이미지")
	private String profileImage;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPwd() {
		return userPwd;
	}
	
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getJoindate() {
		return joindate;
	}
	
	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public int getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public int getReportCount() {
		return reportCount;
	}
	
	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}
	
	public String getProfileImage() {
		return profileImage;
	}
	
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", email=" + email
				+ ", joindate=" + joindate + ", role=" + role + ", delFlag=" + delFlag + ", phoneNumber=" + phoneNumber
				+ ", reportCount=" + reportCount + ", profileImage=" + profileImage + "]";
	}

}
