package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.UserTokenDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;
	
	// ID 중복체크 
	@Override
	public boolean idCheck(String userid) throws Exception {
		if (userid == null || userid.isEmpty()) {
			throw new Exception(); 
		}
		
		return sqlSession.getMapper(UserMapper.class).idCheck(userid) == 1;
	}

	// 회원가입 
	@Override
	public boolean register(UserDto userDto) throws Exception {
		return sqlSession.getMapper(UserMapper.class).register(userDto) == 1;
	}

	// 로그인
	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getUserId() == null || userDto.getUserPwd() == null)
			return null;
		
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	// 사용자 정보 상세조회
	@Override
	public UserDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userid);
	}

	// 사용자 token 받아오기
	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(userid);
	}

	// 사용자 token 저장
	@Override
	public void saveRefreshToken(UserTokenDto userTokenDto) throws Exception {
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(userTokenDto);
	}

	// 사용자 token 삭제
	@Override
	public void deleRefreshToken(UserTokenDto userTokenDto) throws Exception {
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(userTokenDto);
	}

	
	/* 마이페이지 */
	// 회원 정보 조회 
	@Override
	public UserDto getUserInfo(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getUserInfo(userid);
	}
	// 회원 정보 수정 
	@Override
	public boolean updateUserInfo(UserDto userDto) throws Exception {
		return sqlSession.getMapper(UserMapper.class).updateUserInfo(userDto) == 1;
	}
	// 회원 이메일 유효성 확인
	@Override
	public UserDto certifyEmail(UserDto userDto) throws Exception {
		return sqlSession.getMapper(UserMapper.class).certifyEmail(userDto);
	}
	// 비밀번호 변경 
	@Override
	public boolean chageUserPwd(UserDto userDto) throws Exception {
		return sqlSession.getMapper(UserMapper.class).chageUserPwd(userDto) == 1; 
	}
	// 회원 탈퇴 
	@Override
	public boolean withdrawal(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).withdrawal(userid) == 1;
	}
	// 북마크(관심) 매물 목록 조회
	@Override
	public List<HouseProductDto> bookmarkHouseProductList(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).bookmarkHouseProductList(userid);
	}
	// 아파트(매물) 리뷰 목록
	@Override
	public List<HouseProductDto> reviewHouseProductList(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).reviewHouseProductList(userid);
	}
	// 매물 목록 조회 : 해당 기업회원이 올린 매물 목록
	@Override
	public List<HouseProductDto> userProductList(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userProductList(userid);
	}

}
