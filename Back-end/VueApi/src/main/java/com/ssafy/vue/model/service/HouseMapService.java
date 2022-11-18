package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

public interface HouseMapService {

	// 시도정보 호출
	List<SidoGugunCodeDto> getSido() throws Exception;
	// 선택한 시도에 포함된 구군 정보 호출
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	// 선택한 구군에 포함된 동 정보 호출
	List<HouseDealDongDto> getDongInGugun(String gugun) throws Exception;
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	List<HouseDealInfoDto> getAptInDong(HouseParameterDto houseParameterDto) throws Exception;
	
}
