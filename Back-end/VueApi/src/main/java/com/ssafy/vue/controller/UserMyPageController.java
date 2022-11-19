package com.ssafy.vue.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.util.Encrypt;
import com.ssafy.util.ParameterCheck;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BoardReportDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.UserTokenDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/mypage")
@Api("사용자 컨트롤러  API V1")
public class UserMyPageController {

	public static final Logger logger = LoggerFactory.getLogger(UserMyPageController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	// for. 데이터 유효성 검사 
	private ParameterCheck parameterCheck = new ParameterCheck();

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private UserService userService;
	
	
	/* 마이페이지 */
	// 회원 정보 조회 
	@ApiOperation(value = "회원정보 조회", notes = "회원 ID에 해당되는 회원 정보를 반환한다.", response = BoardReportDto.class)
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUserInfo(@PathVariable("userid") @ApiParam(value = "조회할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# UserMyPageController - getUserInfo 회원정보 조회 호출 : {}", userid);
		
		return new ResponseEntity<UserDto>(userService.getUserInfo(userid), HttpStatus.OK);
	}
	
	// 회원 정보 수정 
	@ApiOperation(value = "회원정보 수정", notes = "수정할 회원 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/update")
	public ResponseEntity<String> updateUserInfo(@RequestBody @ApiParam(value = "수정할 회원 정보.", required = true) UserDto userDto) throws Exception {
		logger.info("#Back# UserMyPageController - updateUserInfo 회원정보 수정 호출, 수정할 내용: {}", userDto);
		
		/* (Back) 회원가입 데이터 유효성 check _ ValChecker참조 */
		// 이름 
		String checkResult = parameterCheck.checkNAME(userDto.getUserName());
		if (checkResult == "false" ) {
			logger.info("# 회원정보 수정 - 이름 규칙 체크 Fail-");
			return new ResponseEntity<String>("이름을 다시 작성해주세요", HttpStatus.NO_CONTENT);
		}
		// 이메일 
		checkResult = parameterCheck.checkEmail(userDto.getEmail());
		if (checkResult == "false" ) {
			logger.info("# 회원정보 수정 - 이메일 규칙 체크 Fail-");
			return new ResponseEntity<String>("이메일 형식을 지켜주세요", HttpStatus.NO_CONTENT);
		}
		
		if (userService.updateUserInfo(userDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 비밀번호 변경
	@ApiOperation(value = "비밀번호 변경", notes = "변경할 비밀번호를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/changePwd")
	public ResponseEntity<String> chageUserPwd(@RequestBody @ApiParam(value = "변경할 비밀번호 정보.", required = true) UserDto userDto) throws Exception {
		logger.info("#Back# UserMyPageController - chageUserPwd 비밀번호 변경 호출");
		
		/* (Back) 회원가입 데이터 유효성 check _ ValChecker참조 */
		// PWD
		String checkResult = parameterCheck.checkPassword(userDto.getUserPwd());
		if (checkResult == "false" ) {
			logger.info("# 비밀번호 변경 - 패스워드 규칙 체크 Fail-");
			return new ResponseEntity<String>("패스워드 규칙을 지켜주세요", HttpStatus.NO_CONTENT);
		}
		// 비밀번호 암호화
		try {
			Encrypt encrypt = new Encrypt(); 
			String enc_password = encrypt.hashingWithSHA256(userDto.getUserPwd());	
			userDto.setUserPwd(enc_password);											
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("# 비밀번호 변경 - 패스워드 암호화 Fail-");
			return new ResponseEntity<String>("죄송합니다. 잠시후 다시 시도해주세요", HttpStatus.NO_CONTENT);
		}
		
		
		if (userService.chageUserPwd(userDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 회원 탈퇴
	@ApiOperation(value = "회원 탈퇴", notes = "탈퇴할 회원 ID를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping("/out/{userid}")
	public ResponseEntity<String> withdrawal(@PathVariable("userid") @ApiParam(value = "탈퇴할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# UserMyPageController - withdrawal 회원탈퇴 호출 : {}", userid);
		
		if (userService.withdrawal(userid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 북마크(관심) 매물 목록 조회 
	@ApiOperation(value = "북마크(관심) 매물 목록 조회", notes = "회원이 등록한 북마크 매물 정보를 반환한다.", response = List.class)
	@GetMapping("/bookmark/{userid}")
	public ResponseEntity<List<HouseProductDto>> bookmarkHouseProductList(@PathVariable("userid") @ApiParam(value = "북마크를 조회할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# ReportBoardController - bookmarkHouseProductList 북마크(관심) 매물 목록 조회 호출");
		
		return new ResponseEntity<List<HouseProductDto>>(userService.bookmarkHouseProductList(userid), HttpStatus.OK);
	}
	
	// 아파트(매물) 리뷰 목록
	@ApiOperation(value = "아파트(매물) 리뷰 목록", notes = "회원이 등록한 매물 리뷰 정보를 반환한다.", response = List.class)
	@GetMapping("/review/{writerUserid}")
	public ResponseEntity<List<HouseProductDto>> reviewHouseProductList(@PathVariable("writerUserid") @ApiParam(value = "작성한 리뷰를 조회할 회원 ID", required = true) String writerUserid) throws Exception {
		logger.info("#Back# ReportBoardController - reviewHouseProductList 아파트(매물) 리뷰 목록 조회 호출");
		
		return new ResponseEntity<List<HouseProductDto>>(userService.reviewHouseProductList(writerUserid), HttpStatus.OK);
	}
	
	// 매물 목록 조회 : 해당 기업회원이 올린 매물 목록 
	@ApiOperation(value = "매물 목록 조회", notes = "회원이 등록한 매물 목록을 반환한다.", response = List.class)
	@GetMapping("/product/{userid}")
	public ResponseEntity<List<HouseProductDto>> userProductList(@PathVariable("userid") @ApiParam(value = "등록한 매물을 조회할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# ReportBoardController - userProductList 아파트(매물) 매물 목록 조회 호출");
		
		return new ResponseEntity<List<HouseProductDto>>(userService.userProductList(userid), HttpStatus.OK);
	}
}
