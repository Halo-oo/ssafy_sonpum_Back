package com.ssafy.vue.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BoardReportDto;
import com.ssafy.vue.model.BoardReportImageDto;
import com.ssafy.vue.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/reportBoard")
@Api("전세사기 수법 게시판 컨트롤러  API V1")
public class ReportBoardController {

	private static final Logger logger = LoggerFactory.getLogger(ReportBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

	/* 전세사기 게시글 작성(+ 이미지 업로드, 여러개 가능) */
	// 필요 Dto 데이터: userId, subject, content
	// !! 이미지 업로드 시 files라는 이름(key)으로 form-data로 보내야 함 
	//    file.path.upload-files 경로는 application.properties에 정의되어 있음
	@ApiOperation(value = "전세사기 수법 게시글 작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> reportWriteArticle(
			@Value("${file.path.upload-files}") String filePath,
			@ApiParam(value = "게시글 정보.", required = true) BoardReportDto boardReportDto,
			@RequestParam("files") MultipartFile[] files) throws Exception {
		logger.info("#Back# ReportBoardController - reportWriteArticle 전세사기 수법 게시글 작성 호출");
		
		// FileUpload 관련 설정 
		logger.debug("# 사기 게시글 정보 - 이미지 업로드 MultipartFile.isEmpty 확인 : {}", files[0].isEmpty());
		if (!files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today;
			logger.debug("# 저장 폴더: {}", saveFolder);
			
			File folder = new File(saveFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			List<BoardReportImageDto> boardReportImages = new ArrayList<BoardReportImageDto>();
			for (MultipartFile mfile: files) {
				BoardReportImageDto boardReportImageDto = new BoardReportImageDto();
				String originalFileName = mfile.getOriginalFilename(); 
				if (!originalFileName.isEmpty()) {
					String saveFileName = System.nanoTime() + originalFileName.substring(originalFileName.lastIndexOf('.'));
					boardReportImageDto.setSaveFolder(today);
					boardReportImageDto.setOriginalFileName(originalFileName);
					boardReportImageDto.setSaveFileName(saveFileName);
					logger.debug("# 원본 파일이름: {}, 실제 저장 파일이름: {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				boardReportImages.add(boardReportImageDto);
			}
			boardReportDto.setBoardReportImages(boardReportImages);
		}
		
		// 게시글 등록
		if (boardService.reportWriteArticle(boardReportDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/* 전세사기 게시글 목록 */
	@ApiOperation(value = "전세사기 수법 - 게시판 글 목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardReportDto>> reportListArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("#Back# ReportBoardController - reportListArticle 전세사기 수법 게시판 글 목록 호출, 검색조건: {}", boardParameterDto.toString());
		
		return new ResponseEntity<List<BoardReportDto>>(boardService.reportListArticle(boardParameterDto), HttpStatus.OK);
	}
	
	/* 전세사기 게시글 상세보기(+ 이미지) */
	@ApiOperation(value = "전세사기 수법 - 게시판 글 상세보기", notes = "글 번호에 해당하는 게시글의 정보를 반환한다.", response = BoardReportDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardReportDto> reportGetArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("#Back# ReportBoardController - reportGetArticle 전세사기 수법 게시글 상세보기 호출 : {}", articleno);
		
		// 조회수 update
		boardService.reportUpdateHit(articleno);
		
		return new ResponseEntity<BoardReportDto>(boardService.reportGetArticle(articleno), HttpStatus.OK);
	}
	
	/* 전세사기 수법 게시글 수정 */
	@ApiOperation(value = "전세사기 수법 - 게시판 글 수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> reportModifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardReportDto boardReportDto) throws Exception {
		logger.info("#Back# ReportBoardController - reportModifyArticle 공지사항 게시판 글 수정 호출, 수정할 내용: {}", boardReportDto);
		
		if (boardService.reportModifyArticle(boardReportDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	/* 전세사기 수법 게시글 삭제 */
	// + 해당 게시글과 연관된 이미지 삭제 
	@ApiOperation(value = "전세사기 수법 - 게시판 글 삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> reportDeleteArticle(@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("#Back# ReportBoardController - reportDeleteArticle 전세사기 수법 게시글 삭제 호출");
		
		// 1) 연관된 이미지 삭제 
		if (!boardService.reportDeleteRealtionImage(articleno)) {
			logger.info("#전세사기 수법 삭제 - 이미지 삭제 Fail-");
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
		// 2) 게시글 삭제
		if (boardService.reportDeleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/* 게시글의 특정 이미지 삭제 */
	@ApiOperation(value = "전세사기 수법 - 게시글의 특정 이미지 삭제", notes = "이미지 번호에 해당하는 이미지를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delReportImage/{boardReportImageid}")
	public ResponseEntity<String> reportDeleteImage(@PathVariable("boardReportImageid") @ApiParam(value = "삭제할 이미지 번호", required = true) int boardReportImageid) throws Exception {
		logger.info("#Back# ReportBoardController - reportDeleteImage 전세사기 수법 게시글의 특정 이미지 삭제 호출");
		
		if (boardService.reportDeleteImage(boardReportImageid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}