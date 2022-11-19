package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.HouseProductBookmarkDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.HouseProductParameterDto;
import com.ssafy.vue.model.HouseProductReviewDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;

	/* 아파트 거래정보(시세) */
	// 시도정보 호출
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getSido();
	}
	// 선택한 시도에 포함된 구군 정보 호출
	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}
	// 선택한 구군에 포함된 동 정보 호출
	@Override
	public List<HouseDealDongDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
	}
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	@Override
	public List<HouseDealInfoDto> getAptInDong(HouseParameterDto houseParameterDto) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getAptInDong(houseParameterDto);
	}
	
	
	/* 매물  */
	// 매물 등록
	@Override
	public boolean registerHouseProduct(HouseProductDto houseProductDto) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).registerHouseProduct(houseProductDto) == 1;
	}
	// 매물 목록(+ 검색)
	@Override
	public List<HouseProductDto> listHouseProduct(HouseProductParameterDto houseProductParameterDto) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).listHouseProduct(houseProductParameterDto);
	}
	// 매물 상세보기 
	@Override
	public HouseProductDto getHouseProduct(int houseProductid) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getHouseProduct(houseProductid);
	}
	// 매물 수정
	@Override
	public boolean updateHouseProduct(HouseProductDto houseProductDto) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).updateHouseProduct(houseProductDto) == 1;
	}
	// 매물 판매완료로 변경 
	@Override
	public boolean soldOutHouseProduct(int houseProductid) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).soldOutHouseProduct(houseProductid) == 1;
	}
	// 매물 삭제
	@Override
	public boolean deleteHouseProduct(int houseProductid) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).deleteHouseProduct(houseProductid) == 1;
	}
	// 매물 신고
	@Override
	public boolean reportHouseProduct(String userid) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).reportHouseProduct(userid) == 1;
	}
	// 매물 북마크 등록 
	@Override
	public boolean bookmarkProduct(HouseProductBookmarkDto houseProductBookmarkDto) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).bookmarkProduct(houseProductBookmarkDto) == 1;
	}
	// 매물 리뷰 등록
	@Override
	public boolean reviewProduct(HouseProductReviewDto houseProductReviewDto) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).reviewProduct(houseProductReviewDto) == 1;
	}

}
