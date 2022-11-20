package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.HouseProductBookmarkDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.HouseProductParameterDto;
import com.ssafy.vue.model.HouseProductReviewDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	/* 아파트 거래정보(시세) */
	// 시도정보 호출
	List<SidoGugunCodeDto> getSido() throws SQLException;
	// 선택한 시도에 포함된 구군 정보 호출
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	// 선택한 구군에 포함된 동 정보 호출
	List<HouseDealDongDto> getDongInGugun(String gugun) throws SQLException;
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	List<HouseDealInfoDto> getAptInDong(HouseParameterDto houseParameterDto) throws SQLException;
	
	
	/* 매물  */
	// 매물 등록
	int registerHouseProduct(HouseProductDto houseProductDto) throws SQLException;
	// 매물 등록 시 이미지 등록
	void registerHouseProductImage(HouseProductDto houseProductDto) throws Exception;
	// 매물 목록
	List<HouseProductDto> listHouseProduct(HouseProductParameterDto houseProductParameterDto) throws SQLException;
	// 매물 상세보기 
	HouseProductDto getHouseProduct(int houseProductId) throws SQLException;
	// 매물 수정 
	int updateHouseProduct(HouseProductDto houseProductDto) throws SQLException;
	// 매물 판매완료로 변경 
	int soldOutHouseProduct(int houseProductid) throws SQLException;
	// 매물 삭제 
	int deleteHouseProduct(int houseProductid) throws SQLException;
	// ! 매물 삭제 시 해당 매물과 연관된 리뷰, 북마크 모두 삭제
	int deleteRelationHouseProduct(int houseProductid) throws SQLException; 
	// ! 매물 삭제 시 해당 매물과 연관된 이미지 삭제
	int deleteRelationHouseProductImage(int houseProductid) throws SQLException;
	// 매물 신고
	int reportHouseProduct(String userid) throws SQLException;
	// 매물 북마크 등록
	int bookmarkProduct(HouseProductBookmarkDto houseProductBookmarkDto) throws SQLException;
	// 매물 북마크 해제(삭제)
	int deleteBookmarkProduct(int houseProductBookmarkid) throws SQLException; 
	// 매물 리뷰 등록
	int reviewProduct(HouseProductReviewDto houseProductReviewDto) throws SQLException;
	// 매물 리뷰 목록 
	List<HouseProductReviewDto> reviewProductList(int houseProductid) throws SQLException;  
	// 매물 리뷰 수정
	int editReviewProduct(HouseProductReviewDto houseProductReviewDto) throws SQLException;
	// 매물 리뷰 삭제
	int deleteReviewProduct(int houseProductReviewid) throws SQLException; 
	
}
