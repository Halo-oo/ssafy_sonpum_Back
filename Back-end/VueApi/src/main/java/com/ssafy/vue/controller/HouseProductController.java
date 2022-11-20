package com.ssafy.vue.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.vue.model.BoardReportDto;
import com.ssafy.vue.model.HouseImageDto;
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

	// 매물 등록(+ 이미지 업로드, 여러개 가능)
	// 필요 Dto 데이터: userId, addressId, floor, buildYear, dealAmount, area, dealType, content
	// !! 이미지 업로드 시 files라는 이름(key)으로 form-data로 보내야 함 
	//    file.path.upload-files 경로는 application.properties에 정의되어 있음
	@ApiOperation(value = "매물 등록", notes = "매물을 등록한다.", response = List.class)
	@PostMapping("/register")
	public ResponseEntity<String> registerHouseProduct(
			@Value("${file.path.upload-files}") String filePath,
			@ApiParam(value = "매물 정보", required = true) HouseProductDto houseProductDto, 
			@RequestParam("files") MultipartFile[] files) throws Exception {
		logger.info("#Back# HouseProductController - registerHouseProduct 매물 등록 호출");
		
		// FileUpload 관련 설정 
		logger.debug("# 매물 등록 - 이미지 업로드 MultipartFile.isEmpty 확인 : {}", files[0].isEmpty());;
		if (!files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today; 
			//String saveFolder = "D:/ssafy/Spring/VueApi/src/main/resources/asset" + File.separator + today; 
			logger.debug("# 저장 폴더: {}", saveFolder);
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs(); 
			}
			
			List<HouseImageDto> houseImages = new ArrayList<HouseImageDto>();
			for (MultipartFile mfile: files) {
				HouseImageDto houseImageDto = new HouseImageDto(); 
				String originalFileName = mfile.getOriginalFilename(); 
				if (!originalFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					houseImageDto.setSaveFolder(today);
					houseImageDto.setOriginalFileName(originalFileName);
					houseImageDto.setSaveFileName(saveFileName);
					logger.debug("# 원본 파일이름: {}, 실제 저장 파일이름: {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				houseImages.add(houseImageDto);
			}
			houseProductDto.setHouseImages(houseImages);
		}
		
		// 매물 등록 (이미지 컬럼 없음)
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
	
	// 매물 상세보기(+ 이미지)
	@ApiOperation(value = "매물 상세보기", notes = "매물 번호에 해당하는 매물 정보를 반환한다.", response = BoardReportDto.class)
	@GetMapping("/{houseProductId}")
	public ResponseEntity<HouseProductDto> getHouseProduct(@PathVariable("houseProductId") @ApiParam(value = "얻어올 매물 정보의 번호", required = true) int houseProductId) throws Exception {
		logger.info("#Back# ReportBoardController - getHouseProduct 매물 상세보기 호출 : {}", houseProductId);
		
		return new ResponseEntity<HouseProductDto>(haHouseMapService.getHouseProduct(houseProductId), HttpStatus.OK);
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
	@PutMapping("/sold/{houseProductid}")
	public ResponseEntity<String> soldOutHouseProduct(@PathVariable("houseProductid") @ApiParam(value = "판매완료된 매물 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# HouseProductController - soldOutHouseProduct 매물 판매완료 변경 호출");
		
		if (haHouseMapService.soldOutHouseProduct(houseProductid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 매물 삭제 
	// + 매물 삭제 시 해당 매물과 연관된 리뷰, 북마크 모두 삭제
	@ApiOperation(value = "매물 삭제", notes = "매물번호에 해당되는 정보를 삭제한다.(연관되어 있는 북마크, 리뷰 또한 삭제) 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{houseProductid}")
	public ResponseEntity<String> deleteHouseProduct(@PathVariable("houseProductid") @ApiParam(value = "삭제할 매물 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# HouseProductController - deleteHouseProduct 매물 삭제 호출");
		
		// 해당 매물과 연관된 리뷰, 북마크 모두 삭제
		if (!haHouseMapService.deleteRelationHouseProduct(houseProductid)) {
			logger.info("# 리뷰, 북마크 삭제 실패-");
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
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
	
	// 매물 북마크 해제 
	@ApiOperation(value = "매물 북마크 해제", notes = "해당 매물을 북마크에서 해제한다.", response = List.class)
	@DeleteMapping("/bookmark/{houseProductBookmarkid}")
	public ResponseEntity<String> deleteBookmarkProduct(@PathVariable("houseProductBookmarkid") @ApiParam(value = "북마크를 해제할 매물 번호", required = true) int houseProductBookmarkid) throws Exception {
		logger.info("#Back# HouseProductController - deleteBookmarkProduct 매물 북마크 해제 호출");
		
		if (haHouseMapService.deleteBookmarkProduct(houseProductBookmarkid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 매물 리뷰 등록
	// 필요 Dto 데이터: houseProductId, writerUserId, rating, buildYear, content, image
	@ApiOperation(value = "매물 리뷰 등록", notes = "해당 매물의 리뷰를 등록한다.", response = List.class)
	@PostMapping("/review")
	public ResponseEntity<String> reviewProduct(@RequestBody @ApiParam(value = "매물 리뷰 등록", required = true) HouseProductReviewDto houseProductReviewDto) throws Exception {
		logger.info("#Back# HouseProductController - reviewProduct 매물 리뷰 등록 호출");
		
		if (haHouseMapService.reviewProduct(houseProductReviewDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 매물 리뷰 목록 
	@ApiOperation(value = "매물 리뷰 목록", notes = "매물 번호에 해당하는 매물 리뷰 반환", response = List.class)
	@GetMapping("/review/{houseProductid}")
	public ResponseEntity<List<HouseProductReviewDto>> reviewProductList(@PathVariable("houseProductid") @ApiParam(value = "리뷰를 가져올 매물 번호", required = true) int houseProductid) throws Exception {
		logger.info("#Back# HouseProductController - reviewProductList 매물 리뷰 목록 호출");
		
		return new ResponseEntity<List<HouseProductReviewDto>>(haHouseMapService.reviewProductList(houseProductid), HttpStatus.OK);
	}
	
	// 매물 리뷰 수정 
	// 필요 Dto 데이터: houseProductReviewId, rating, content, image
	@ApiOperation(value = "매물 리뷰 수정", notes = "해당 매물의 리뷰를 수정한다.", response = List.class)
	@PostMapping("/reviewEdit")
	public ResponseEntity<String> editReviewProduct(@RequestBody @ApiParam(value = "매물 리뷰 수정", required = true) HouseProductReviewDto houseProductReviewDto) throws Exception {
		logger.info("#Back# HouseProductController - editReviewProduct 매물 리뷰 수정 호출");
		
		if (haHouseMapService.editReviewProduct(houseProductReviewDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 매물 리뷰 삭제 
	@ApiOperation(value = "매물 리뷰 삭제", notes = "리뷰번호에 해당되는 리뷰를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/review/{houseProductReviewid}")
	public ResponseEntity<String> deleteReviewProduct(@PathVariable("houseProductReviewid") @ApiParam(value = "삭제할 리뷰 번호", required = true) int houseProductReviewid) throws Exception {
		logger.info("#Back# HouseProductController - deleteReviewProduct 매물 리뷰 삭제 호출");
		
		if (haHouseMapService.deleteReviewProduct(houseProductReviewid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
