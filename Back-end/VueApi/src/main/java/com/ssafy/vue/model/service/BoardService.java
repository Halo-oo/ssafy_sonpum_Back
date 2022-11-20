package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BoardReportDto;

public interface BoardService {
	/* 공지사항 */
	// 게시글 작성
	public boolean noticeWriteArticle(BoardNoticeDto boardNoticeDto) throws Exception;
	// 게시글 목록
	public List<BoardNoticeDto> noticeListArticle(BoardParameterDto boardParameterDto) throws Exception;
	//public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	// 게시글 상세보기
	public BoardNoticeDto noticeGetArticle(int articleno) throws Exception;
	// 조회수
	public void noticeUpdateHit(int articleno) throws Exception;
	// 수정
	public boolean noticeModifyArticle(BoardNoticeDto boardNoticeDto) throws Exception;
	// 삭제
	public boolean noticeDeleteArticle(int articleno) throws Exception;
	
	
	/* 전세사기 수법 게시판 */
	// 게시글 작성
	public boolean reportWriteArticle(BoardReportDto boardReportDto) throws Exception;
	// 게시글 목록
	public List<BoardReportDto> reportListArticle(BoardParameterDto boardParameterDto) throws Exception;
	// 게시글 상세보기
	public BoardReportDto reportGetArticle(int articleno) throws Exception;
	// 조회수
	public void reportUpdateHit(int articleno) throws Exception;
	// 수정
	public boolean reportModifyArticle(BoardReportDto boardReportDto) throws Exception;
	// 삭제
	public boolean reportDeleteArticle(int articleno) throws Exception;
}
