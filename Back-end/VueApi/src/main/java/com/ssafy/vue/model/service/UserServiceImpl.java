package com.ssafy.vue.model.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;

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
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
	}

	// 사용자 token 삭제
	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}

}
