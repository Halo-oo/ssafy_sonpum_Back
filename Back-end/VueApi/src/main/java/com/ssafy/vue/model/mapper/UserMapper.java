package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.UserTokenDto;

@Mapper
public interface UserMapper {
	
	// ID 중복체크 
	int idCheck(String userid) throws SQLException; 
	// 회원가입 
	int register(UserDto userDto) throws SQLException; 
	
	// 로그인
	public UserDto login(UserDto userDto) throws SQLException;
	// 사용자 정보 상세조회
	public UserDto userInfo(String userid) throws SQLException;
	// 사용자 token 받아오기
	public Object getRefreshToken(String userid) throws SQLException;
	// 사용자 token 저장
	public void saveRefreshToken(UserTokenDto userTokenDto) throws SQLException;
	// 사용자 token 삭제
	public void deleteRefreshToken(UserTokenDto userTokenDto) throws SQLException;
	
}
