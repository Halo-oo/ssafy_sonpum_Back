package com.ssafy.vue.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ssafy.vue.model.BoardReportDto;
import com.ssafy.vue.model.HouseProductDto;
import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.MailService;
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
	
	// for. 이메일 전송 
	@Autowired
	private MailService mailService; 

	// for. token
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
	
	// 이메일 인증코드 전송
	// 필요 Dto 데이터: userId, email
	@ApiOperation(value = "이메일 인증코드 전송", notes = "전송한 인증코드를 반환한다.", response = String.class)
	@PostMapping("/certify")
	public ResponseEntity<Map<String, Object>> certifyEmail(@RequestBody @ApiParam(value = "인증받을 이메일 주소", required = true) UserDto userDto) throws Exception {
		logger.info("#Back# UserMyPageController - certifyEmail 이메일 인증코드 전송 호출 - {}, {}", userDto.getUserId(), userDto.getEmail());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null; 
		
		// ! 이메일 형식 검사 
		String checkResult = parameterCheck.checkEmail(userDto.getEmail());
		if (checkResult == "false" ) {
			logger.info("# 이메일 인증코드 전송 - 이메일 규칙 체크 Fail-");
			resultMap.put("message", "이메일 형식을 지켜주세요");
			status = HttpStatus.NO_CONTENT;
			return new ResponseEntity<Map<String,Object>>(resultMap, status);
		}
		// ! 현재 user와 email이 일치하는지 검사  
		String email = userService.certifyEmail(userDto).getEmail();
		if (!(email.equals(userDto.getEmail()))) {
			logger.info("# 이메일 인증코드 전송 - 존재하지 않는 사용자 이메일, Fail-");
			resultMap.put("message", "이메일을 다시 입력해주세요");
			status = HttpStatus.NO_CONTENT;
			return new ResponseEntity<Map<String,Object>>(resultMap, status);
		}
		
		// 메일 전송
		String code = mailService.sendMail(userDto.getEmail());
		logger.info("# 이메일 인증코드 : {}", code);
		if (code.equals("error")) {
			logger.info("# 이메일 전송 Fail-");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		else {
			logger.info("# 이메일 전송 Success-");
			resultMap.put("message", SUCCESS); 
			resultMap.put("code", code);
			status = HttpStatus.ACCEPTED;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);		
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
	public ResponseEntity<Map<String, Object>> withdrawal(@PathVariable("userid") @ApiParam(value = "탈퇴할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# UserMyPageController - withdrawal 회원탈퇴 호출 : {}", userid);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		if (userService.withdrawal(userid)) {
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		}
		else {
			resultMap.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
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
	@GetMapping("/review/{userid}")
	public ResponseEntity<List<HouseProductDto>> reviewHouseProductList(@PathVariable("userid") @ApiParam(value = "작성한 리뷰를 조회할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# ReportBoardController - reviewHouseProductList 아파트(매물) 리뷰 목록 조회 호출");
		
		return new ResponseEntity<List<HouseProductDto>>(userService.reviewHouseProductList(userid), HttpStatus.OK);
	}
	
	// 매물 목록 조회 : 해당 기업회원이 올린 매물 목록 
	@ApiOperation(value = "매물 목록 조회", notes = "회원이 등록한 매물 목록을 반환한다.", response = List.class)
	@GetMapping("/product/{userid}")
	public ResponseEntity<List<HouseProductDto>> userProductList(@PathVariable("userid") @ApiParam(value = "등록한 매물을 조회할 회원 ID", required = true) String userid) throws Exception {
		logger.info("#Back# ReportBoardController - userProductList 아파트(매물) 매물 목록 조회 호출");
		
		return new ResponseEntity<List<HouseProductDto>>(userService.userProductList(userid), HttpStatus.OK);
	}
}
