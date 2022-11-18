package com.ssafy.vue.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.util.Encrypt;
import com.ssafy.util.ParameterCheck;
import com.ssafy.vue.model.UserDto;
import com.ssafy.vue.model.UserTokenDto;
import com.ssafy.vue.model.service.JwtServiceImpl;
import com.ssafy.vue.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	// for. 데이터 유효성 검사 
	private ParameterCheck parameterCheck = new ParameterCheck();

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private UserService userService;
	
	// ID 중복체크 
	@ApiOperation(value = "ID 중복체크", notes = "동일한 ID가 있는 지 체크", response = Map.class)
	@PostMapping("/idCheck/{userid}")
	public ResponseEntity<String> idCheck(@PathVariable("userid") @ApiParam(value = "중복 체크를 진행할 회원의 아이디.", required = true) String userid) throws Exception {
		logger.info("#Back# UserController - idCheck ID 중복체크 {}", userid);
			
		if (userService.idCheck(userid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 회원가입
	@ApiOperation(value = "회원가입", notes = "회원 가입", response = Map.class)
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @ApiParam(value = "회원가입 시 필요한 회원정보", required = true) UserDto userDto) throws Exception {
		logger.info("#Back# UserController - register 회원가입");
		
		/* (Back) 회원가입 데이터 유효성 check _ ValChecker참조 */
		// ID
		String checkResult = parameterCheck.checkEmpty(userDto.getUserId());
		if (checkResult == "false" ) {
			logger.info("# 회원가입 - ID 체크 Fail-");
			return new ResponseEntity<String>("ID를 입력하세요.", HttpStatus.NO_CONTENT);
		}
		// PWD
		checkResult = parameterCheck.checkPassword(userDto.getUserPwd());
		if (checkResult == "false" ) {
			logger.info("# 회원가입 - 패스워드 규칙 체크 Fail-");
			return new ResponseEntity<String>("패스워드 규칙을 지켜주세요", HttpStatus.NO_CONTENT);
		}
		// 이름 
		checkResult = parameterCheck.checkNAME(userDto.getUserName());
		if (checkResult == "false" ) {
			logger.info("# 회원가입 - 이름 규칙 체크 Fail-");
			return new ResponseEntity<String>("이름을 다시 작성해주세요", HttpStatus.NO_CONTENT);
		}
		// 이메일 
		checkResult = parameterCheck.checkEmail(userDto.getEmail());
		if (checkResult == "false" ) {
			logger.info("# 회원가입 - 이메일 규칙 체크 Fail-");
			return new ResponseEntity<String>("이메일 형식을 지켜주세요", HttpStatus.NO_CONTENT);
		}
		
		// 비밀번호 암호화
		try {
			Encrypt encrypt = new Encrypt(); 
			String enc_password = encrypt.hashingWithSHA256(userDto.getUserPwd());	// 암호화 진행
			userDto.setUserPwd(enc_password);											// 암호화 된 패스워드 저장
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
		if (userService.register(userDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	// 로그인
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		logger.info("#Back# UserController - login 로그인");
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		try {
			// 비밀번호 암호화 
			try {
				Encrypt encrypt = new Encrypt(); 
				String enc_password = encrypt.hashingWithSHA256(userDto.getUserPwd());	// 암호화 진행
				userDto.setUserPwd(enc_password);										// userDto 내 암호화한 비밀번호 저장
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			UserDto loginUser = userService.login(userDto);
			
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId());			// key, data
				String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId());		// key, data
				
				UserTokenDto userTokenDto = new UserTokenDto(); 
				userTokenDto.setUserId(userDto.getUserId());
				userTokenDto.setToken(refreshToken);
				userService.saveRefreshToken(userTokenDto);
				logger.debug("# 로그인 accessToken 정보 : {}", accessToken);
				logger.debug("# 로그인 refreshToken 정보 : {}", refreshToken);
				
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
			
		} catch (Exception e) {
			logger.error("# 로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 사용자 정보 상세조회, 회원 인증(회원 token 받아오기)
	@ApiOperation(value = "회원 인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid, HttpServletRequest request) {
		logger.info("#Back# UserController - getInfo 회원 인증(회원 token 받아오기), 인증할 회원 아이디: {}", userid);
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		logger.info("# access-token 확인: {}", request.getHeader("access-token"));		// !! 애초에 Header에서 못가지고 옴
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("# 사용 가능한 토큰!");
			try {
				// 로그인 사용자 정보.
				UserDto memberDto = userService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
				
			} catch (Exception e) {
				logger.error("# 정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
			
		} else {
			logger.error("# 사용 불가능 토큰!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 사용자 token 재발급
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		logger.info("#Back# UserController - refreshToken 사용자 token 재발급(Access Token 재발급)");
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("# token : {}, userDto : {}", token, userDto);
		
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				logger.debug("# token : {}", accessToken);
				logger.debug("# 정상적으로 액세스토큰 재발급 완료");
				resultMap.put("# access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
			
		} else {
			logger.debug("# 리프레쉬토큰도 사용불가!");
			status = HttpStatus.UNAUTHORIZED;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 로그아웃(token 삭제)
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		logger.info("#Back# UserController - removeToken 로그아웃(token 삭제)");
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			UserTokenDto userTokenDto = new UserTokenDto(); 
			userTokenDto.setUserId(userid);
			userService.deleRefreshToken(userTokenDto);
			resultMap.put("# message", SUCCESS);
			status = HttpStatus.ACCEPTED;
			
		} catch (Exception e) {
			logger.error("# 로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
