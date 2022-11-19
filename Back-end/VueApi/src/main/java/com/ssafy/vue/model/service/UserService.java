package com.ssafy.vue.model.service;

import java.sql.SQLException;

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
	
}
