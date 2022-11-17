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

import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/noticeBoard")
@Api("게시판 컨트롤러  API V1")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

	/* 공지사항 - 게시판 글 작성 */
	@ApiOperation(value = "공지사항 - 게시판 글 작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> noticeWriteArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardNoticeDto boardNoticeDto) throws Exception {
		logger.info("#Back# BoardController - noticeWriteArticle 공지사항 게시글 작성 호출");
		
		if (boardService.noticeWriteArticle(boardNoticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/* 공지사항 - 게시판 글 목록 */
	@ApiOperation(value = "공지사항 - 게시판 글 목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardNoticeDto>> noticeListArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("#Back# BoardController - noticeListArticle 공지사항 게시판 글 목록 호출, 검색조건: {}", boardParameterDto.toString());
		
		return new ResponseEntity<List<BoardNoticeDto>>(boardService.noticeListArticle(boardParameterDto), HttpStatus.OK);
	}
	
	/* 공지사항 - 게시판 글 상세보기 */
	@ApiOperation(value = " 공지사항 - 게시판 글 상세보기", notes = "글 번호에 해당하는 게시글의 정보를 반환한다.", response = BoardNoticeDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardNoticeDto> noticeGetArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("#Back# BoardController - noticeGetArticle 공지사항 게시글 상세보기 호출 : " + articleno);
		
		// 조회수 update
		boardService.noticeUpdateHit(articleno);		 
		
		return new ResponseEntity<BoardNoticeDto>(boardService.noticeGetArticle(articleno), HttpStatus.OK);
	}
	
	/* 공지사항 - 게시판 글 수정 */
	@ApiOperation(value = "공지사항 - 게시판 글 수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> noticeModifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardNoticeDto boardNoticeDto) throws Exception {
		logger.info("#Back# BoardController - noticeModifyArticle 공지사항 게시판 글 수정 호출, 수정할 내용: {}", boardNoticeDto);
		
		if (boardService.noticeModifyArticle(boardNoticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	/* 공지사항 - 게시판 글 삭제 */
	@ApiOperation(value = "공지사항 - 게시판 글 삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> noticeDeleteArticle(@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("#Back# BoardController - noticeDeleteArticle 공지사항 게시글 삭제 호출");
		
		if (boardService.noticeDeleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}