package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.BoardReportDto;
import com.ssafy.vue.model.HouseProductBookmarkDto;
import com.ssafy.vue.model.HouseProductCheckDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.HouseProductParameterDto;
import com.ssafy.vue.model.HouseProductReviewDto;
import com.ssafy.vue.model.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/houseProduct")
@Api("Map 컨트롤러  API V1")
public class HouseProductController {

	private final Logger logger = LoggerFactory.getLogger(HouseProductController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private HouseMapService haHouseMapService;

	// 매물 등록
	@ApiOperation(value = "매물 등록", notes = "매물을 등록한다.", response = List.class)
	@PostMapping("/register")
	public ResponseEntity<String> registerHouseProduct(@RequestBody @ApiParam(value = "매물 정보", required = true) HouseProductDto houseProductDto) throws Exception {
		logger.info("#Back# HouseProductController - registerHouseProduct 매물 등록 호출");
		
		if (haHouseMapService.registerHouseProduct(houseProductDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	// 매물 목록(+ 검색)
	@ApiOperation(value = "매물 목록(+ 검색)", notes = "아파트 코드(addressId)에 해당하는 매물 목록 반환", response = List.class)
	@PostMapping("/list")
	public ResponseEntity<List<HouseProductDto>> listHouseProduct(@ApiParam(value = "매물 목록을 검색하기 위한 부가 정보", required = true) HouseProductParameterDto houseProductParameterDto) throws Exception {
		logger.info("#Back# HouseProductController - listHouseProduct 매물 목록 호출, 검색조건: {}", houseProductParameterDto);
		
		// 매매금액을 검색할 경우 사전 처리 필요
		if (houseProductParameterDto.getKey()!=null && houseProductParameterDto.getKey().equals("amount")) {
			if (houseProductParameterDto.getWord()!=null && houseProductParameterDto.getWord()!="") {
				String cost = houseProductParameterDto.getWord(); 
				logger.info("# 매매금액 검색 사전작업 결과: " + cost.substring(0, cost.length()-3));
				houseProductParameterDto.setWord(cost.substring(0, cost.length()-3));
			}
		}
		
		return new ResponseEntity<List<HouseProductDto>>(haHouseMapService.listHouseProduct(houseProductParameterDto), HttpStatus.OK);
	}
	
	// 매물 상세보기 
	@ApiOperation(value = "매물 상세보기", notes = "매물 번호에 해당하는 매물 정보를 반환한다.", response = BoardReportDto.class)
	@GetMapping("/{houseProductid}")
	public ResponseEntity<HouseProductDto> getHouseProduct(@PathVariable("houseProductid") @ApiParam(value = "얻어올 매물 정보의 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# ReportBoardController - getHouseProduct 매물 상세보기 호출 : {}", houseProductid);
		
		return new ResponseEntity<HouseProductDto>(haHouseMapService.getHouseProduct(houseProductid), HttpStatus.OK);
	}
	
	// 매물 수정 
	@ApiOperation(value = "매물 수정", notes = "매물에 대해 수정할 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateHouseProduct(@RequestBody @ApiParam(value = "수정할 매물 정보", required = true) HouseProductDto houseProductDto) throws Exception {
		logger.info("#Back# HouseProductController - updateHouseProduct 매물 수정 호출, 수정할 내용: {}", houseProductDto);
		
		if (haHouseMapService.updateHouseProduct(houseProductDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 매물 판매완료로 변경 
	@ApiOperation(value = "매물 판매완료", notes = "매물을 판매완료로 변경한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/{houseProductid}")
	public ResponseEntity<String> soldOutHouseProduct(@PathVariable("houseProductid") @ApiParam(value = "판매완료된 매물 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# HouseProductController - soldOutHouseProduct 매물 판매완료 변경 호출");
		
		if (haHouseMapService.soldOutHouseProduct(houseProductid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 매물 삭제 
	@ApiOperation(value = "매물 삭제", notes = "매물번호에 해당되는 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{houseProductid}")
	public ResponseEntity<String> deleteHouseProduct(@PathVariable("houseProductid") @ApiParam(value = "삭제할 매물 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# HouseProductController - deleteHouseProduct 매물 삭제 호출");
		
		if (haHouseMapService.deleteHouseProduct(houseProductid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	 
	// 매물 판매자 신고 
	@ApiOperation(value = "매물 판매자 신고", notes = "해당 매물을 판매하는 기업회원을 신고한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/report/{userid}")
	public ResponseEntity<String> reportHouseProduct(@PathVariable("userid") @ApiParam(value = "신고할 기업회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# HouseProductController - reportHouseProduct 매물 판매자 신고 호출");
		
		if (haHouseMapService.reportHouseProduct(userid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 매물 안전성 검사 (! 미완)
	@ApiOperation(value = "매물 안전성 검사", notes = "선택한 매물이 안전한 매물인지에 대한 검사 진행. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/check")
	public ResponseEntity<String> checkHouseProduct(@RequestBody @ApiParam(value = "확인할 매물 정보", required = true) HouseProductCheckDto houseProductCheckDto) throws Exception {
		logger.info("#Back# HouseProductController - checkHouseProduct 매물 안전성 확인 호출, 확인할 내용: {}", houseProductCheckDto);
		
		
//		if (haHouseMapService.updateHouseProduct(houseProductDto)) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 매물 북마크 등록 
	@ApiOperation(value = "매물 북마크 등록", notes = "해당 매물을 북마크 등록한다.", response = List.class)
	@PostMapping("/bookmark")
	public ResponseEntity<String> bookmarkProduct(@RequestBody @ApiParam(value = "매물 북마크", required = true) HouseProductBookmarkDto houseProductBookmarkDto) throws Exception {
		logger.info("#Back# HouseProductController - bookmarkProduct 매물 북마크 등록 호출");
		
		if (haHouseMapService.bookmarkProduct(houseProductBookmarkDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 매물 리뷰 등록
	@ApiOperation(value = "매물 리뷰 등록", notes = "해당 매물의 리뷰를 등록한다.", response = List.class)
	@PostMapping("/review")
	public ResponseEntity<String> reviewProduct(@RequestBody @ApiParam(value = "매물 리뷰", required = true) HouseProductReviewDto houseProductReviewDto) throws Exception {
		logger.info("#Back# HouseProductController - reviewProduct 매물 리뷰 등록 호출");
		
		if (haHouseMapService.reviewProduct(houseProductReviewDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
