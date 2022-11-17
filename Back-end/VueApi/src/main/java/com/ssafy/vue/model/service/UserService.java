package com.ssafy.vue.model.service;

import com.ssafy.vue.model.UserDto;

public interface UserService {

	// 로그인
	public UserDto login(UserDto userDto) throws Exception;
	// 사용자 정보 상세조회
	public UserDto userInfo(String userid) throws Exception;
	// 사용자 token 받아오기
	public Object getRefreshToken(String userid) throws Exception;
	// 사용자 token 저장
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	// 사용자 token 삭제
	public void deleRefreshToken(String userid) throws Exception;
	
}
