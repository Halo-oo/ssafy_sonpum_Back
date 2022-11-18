package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	// 시도정보 호출
	List<SidoGugunCodeDto> getSido() throws SQLException;
	// 선택한 시도에 포함된 구군 정보 호출
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	// 선택한 구군에 포함된 동 정보 호출
	List<HouseDealDongDto> getDongInGugun(String gugun) throws SQLException;
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	List<HouseDealInfoDto> getAptInDong(HouseParameterDto houseParameterDto) throws SQLException;
	
}
