package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.UserTokenDto;

public interface UserService {
	
	// ID 중복체크 
	boolean idCheck(String userid) throws Exception; 
	// 회원가입 
	boolean register(UserDto userDto) throws Exception; 

	// 로그인
	public UserDto login(UserDto userDto) throws Exception;
	// 사용자 정보 상세조회
	public UserDto userInfo(String userid) throws Exception;
	// 사용자 token 받아오기
	public Object getRefreshToken(String userid) throws Exception;
	// 사용자 token 저장
	public void saveRefreshToken(UserTokenDto userTokenDto) throws Exception;
	// 사용자 token 삭제
	public void deleRefreshToken(UserTokenDto userTokenDto) throws Exception;
	
	
	/* 마이페이지 */
	// 회원 정보 조회 
	UserDto getUserInfo(String userid) throws Exception; 
	// 회원 정보 수정 
	boolean updateUserInfo(UserDto userDto) throws Exception; 
	// 회원 이메일 유효성 확인 
	UserDto certifyEmail(UserDto userDto) throws Exception; 
	// 비밀번호 변경 
	boolean chageUserPwd(UserDto userDto) throws Exception;
	// 회원 탈퇴 
	boolean withdrawal(String userid) throws Exception; 
	// 북마크(관심) 매물 목록 조회
	List<HouseProductDto> bookmarkHouseProductList(String userid) throws Exception; 
	// 아파트(매물) 리뷰 목록
	List<HouseProductDto> reviewHouseProductList(String writerUserid) throws Exception;
	// 매물 목록 조회 : 해당 기업회원이 올린 매물 목록
	List<HouseProductDto> userProductList(String userid) throws Exception; 
	
}
