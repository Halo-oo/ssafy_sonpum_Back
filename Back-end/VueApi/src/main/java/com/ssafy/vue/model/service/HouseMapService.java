package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.HouseProductBookmarkDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.HouseProductParameterDto;
import com.ssafy.vue.model.HouseProductReviewDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

public interface HouseMapService {

	/* 아파트 거래정보(시세) */
	// 시도정보 호출
	List<SidoGugunCodeDto> getSido() throws Exception;
	// 선택한 시도에 포함된 구군 정보 호출
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	// 선택한 구군에 포함된 동 정보 호출
	List<HouseDealDongDto> getDongInGugun(String gugun) throws Exception;
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	List<HouseDealInfoDto> getAptInDong(HouseParameterDto houseParameterDto) throws Exception;
	
	/* 매물  */
	// 매물 등록
	boolean registerHouseProduct(HouseProductDto houseProductDto) throws Exception;
	// 매물 목록
	List<HouseProductDto> listHouseProduct(HouseProductParameterDto houseProductParameterDto) throws Exception;
	// 매물 상세보기 
	HouseProductDto getHouseProduct(int houseProductId) throws Exception;
	// 매물 수정 
	boolean updateHouseProduct(HouseProductDto houseProductDto) throws Exception;
	// 매물 판매완료로 변경 
	boolean soldOutHouseProduct(int houseProductid) throws Exception;
	// 매물 삭제 
	boolean deleteHouseProduct(int houseProductid) throws Exception;
	// ! 매물 삭제 시 해당 매물과 연관된 리뷰, 북마크 모두 삭제
	boolean deleteRelationHouseProduct(int houseProductid) throws Exception; 
	// 매물 신고
	boolean reportHouseProduct(String userid) throws Exception;
	// 매물 북마크 등록
	boolean bookmarkProduct(HouseProductBookmarkDto houseProductBookmarkDto) throws Exception;
	// 매물 북마크 해제(삭제)
	boolean deleteBookmarkProduct(int houseProductBookmarkid) throws Exception; 
	// 매물 리뷰 등록
	boolean reviewProduct(HouseProductReviewDto houseProductReviewDto) throws Exception;
	// 매물 리뷰 목록 
	List<HouseProductReviewDto> reviewProductList(int houseProductid) throws Exception; 
	// 매물 리뷰 수정
	boolean editReviewProduct(HouseProductReviewDto houseProductReviewDto) throws Exception;
	// 매물 리뷰 삭제
	boolean deleteReviewProduct(int houseProductReviewid) throws Exception; 
	
}
