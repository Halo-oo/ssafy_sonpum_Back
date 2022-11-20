package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BoardReportDto;

@Mapper
public interface BoardMapper {
	
	/* 공지사항 게시판 */
	// 게시글 작성
	public int noticeWriteArticle(BoardNoticeDto boardNoticeDto) throws SQLException;
	// 게시글 목록
	public List<BoardNoticeDto> noticeListArticle(BoardParameterDto boardParameterDto) throws SQLException;
	// 게시글 총 개수
	public int noticeGetTotalCount(BoardParameterDto boardParameterDto) throws SQLException;	
	// 게시글 상세보기
	public BoardNoticeDto noticeGetArticle(int articleno) throws SQLException;
	// 조회수
	public void noticeUpdateHit(int articleno) throws SQLException;
	// 수정
	public int noticeModifyArticle(BoardNoticeDto boardNoticeDto) throws SQLException;
	// 삭제
	public int noticeDeleteArticle(int articleno) throws SQLException;
	
	
	/* 전세사기 수법 게시판 */
	// 게시글 작성
	int reportWriteArticle(BoardReportDto boardReportDto) throws SQLException; 
	// 게시글 작성 시 이미지 등록
	void reportWriteArticleImage(BoardReportDto boardReportDto) throws Exception;
	// 게시글 목록
	List<BoardReportDto> reportListArticle(BoardParameterDto boardParameterDto) throws SQLException;
	// 게시글 목록 총 개수
	int reportGetTotalCount(BoardParameterDto boardParameterDto) throws SQLException;	
	// 게시글 상세보기
	BoardReportDto reportGetArticle(int articleno) throws SQLException;
	// 게시글 조회수 증가
	void reportUpdateHit(int articleno) throws SQLException;
	// 게시글 수정
	int reportModifyArticle(BoardReportDto boardReportDto) throws SQLException;
	// 게시글 삭제
	int reportDeleteArticle(int articleno) throws SQLException;
	// ! 게시글 삭제 시 연관된 이미지 삭제 
	int reportDeleteRealtionImage(int articleno) throws SQLException; 
	// 특정 게시글의 이미지 삭제
	int reportDeleteImage(int boardReportImageid) throws SQLException; 
	
}