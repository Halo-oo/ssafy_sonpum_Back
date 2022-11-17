package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.UserDto;

@Mapper
public interface UserMapper {

	// 로그인
	public UserDto login(UserDto userDto) throws SQLException;
	// 사용자 정보 상세조회
	public UserDto userInfo(String userid) throws SQLException;
	// 사용자 token 받아오기
	public Object getRefreshToken(String userid) throws SQLException;
	// 사용자 token 저장
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	// 사용자 token 삭제
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
}
