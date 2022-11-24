package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.HouseProductDto;
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
	
	
	/* 마이페이지 */
	// 회원 정보 조회 
	UserDto getUserInfo(String userid) throws SQLException; 
	// 회원 정보 수정 
	int updateUserInfo(UserDto userDto) throws SQLException; 
	// 회원 이메일 인증 
	UserDto certifyEmail(UserDto userDto) throws SQLException;
	// 비밀번호 변경 
	int chageUserPwd(UserDto userDto) throws SQLException;
	// 회원 탈퇴 
	int withdrawal(String userid) throws SQLException; 
	// 북마크(관심) 매물 목록 조회
	List<HouseProductDto> bookmarkHouseProductList(String userid) throws SQLException; 
	// 아파트(매물) 리뷰 목록
	List<HouseProductDto> reviewHouseProductList(String userid) throws SQLException;
	// 매물 목록 조회 : 해당 기업회원이 올린 매물 목록
	List<HouseProductDto> userProductList(String userid) throws SQLException; 
}
