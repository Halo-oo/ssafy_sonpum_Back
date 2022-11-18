package com.ssafy.vue.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.HouseDealDongDto;
import com.ssafy.vue.model.HouseDealInfoDto;
import com.ssafy.vue.model.HouseParameterDto;
import com.ssafy.vue.model.SidoGugunCodeDto;
import com.ssafy.vue.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
public class HouseMapController {

	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;

	// 시도 정보 반환
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("#Back# HouseMapController - sido 시도 정보 반환 호출");
		
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}

	// 선택한 시도에 포함된 구군 정보 호출
	@ApiOperation(value = "구군 정보", notes = "선택한 시도에 포함된 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("#Back# HouseMapController - gugun 선택한 시도에 포함된 구군 정보 호출, 선택한 시: {}", sido);
		
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}

	// 선택한 구군에 포함된 동 정보 호출
	@ApiOperation(value = "동 정보", notes = "선택한 시도에 포함된 동을 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseDealDongDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		logger.info("#Back# HouseMapController - dong 선택한 구군에 포함된 동 정보 호출, 선택한 구군: {}", gugun);
		
		return new ResponseEntity<List<HouseDealDongDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	// 선택한 동에 포함된 아파트 정보 호출(+ 검색)
	// 검색조건: amount(매매금액) 이하, area(면적) 이상, floor(층) 이상, buildYear(건설년도) 이상, apartName(아파트 이름), dealDate(거래날짜, ex. 2022315)
	@ApiOperation(value = "아파트 거래내역 목록(검색 포함)", notes = "선택한 동에 포함된 아파트 정보를 반환한다.", response = List.class)
	@GetMapping("/apt")
	public ResponseEntity<List<HouseDealInfoDto>> apt(@ApiParam(value = "아파트 목록 검색을 위한 부가정보", required = true) HouseParameterDto houseParameterDto) throws Exception {
		logger.info("#Back# HouseMapController - apt 선택한 동에 포함된 아파트 정보 호출, 선택한 동 및 검색조건: {}", houseParameterDto);
		
		// 매매금액을 검색할 경우 사전 처리 필요
		if (houseParameterDto.getKey()!=null && houseParameterDto.getKey().equals("amount")) {
			if (houseParameterDto.getWord()!=null && houseParameterDto.getWord()!="") {
				String cost = houseParameterDto.getWord(); 
				logger.info("# 매매금액 검색 사전작업 결과: " + cost.substring(0, cost.length()-3));
				houseParameterDto.setWord(cost.substring(0, cost.length()-3));
			}
		}
		
		return new ResponseEntity<List<HouseDealInfoDto>>(haHouseMapService.getAptInDong(houseParameterDto), HttpStatus.OK);
	}
	
	// 아파트 거래내역 목록  API
	@ApiOperation(value = "아파트 거래내역 목록 - API", notes = "지역코드와 매매계약월을 기준으로 아파트 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/aptlist/{lawd_cd}/{deal_ymd}", produces = "application/json;charset=utf-8")
	public ResponseEntity<String> aptList(@PathVariable("lawd_cd") String lawdCd, @PathVariable("deal_ymd") String dealYmd) throws IOException {
		logger.info("#Back# HouseMapController - aptList 아파트 목록 호출, 선택한 지역코드: {}, 매매계약월: {}", lawdCd, dealYmd);
		
		String serviceKey = "9Xo0vlglWcOBGUDxH8PPbuKnlBwbWU6aO7%2Bk3FV4baF9GXok1yxIEF%2BIwr2%2B%2F%2F4oVLT8bekKU%2Bk9ztkJO0wsBw%3D%3D";
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); 
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=" + serviceKey);
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append(
				"&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(lawdCd, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(dealYmd, "UTF-8")); /* 계약월 */
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("# 아파트 목록 호출 Response code: " + conn.getResponseCode());
		
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		System.out.println("# " + sb.toString());
		JSONObject json = XML.toJSONObject(sb.toString());
		String jsonStr = json.toString();

		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
	}

}
