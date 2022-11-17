package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;

@Mapper
public interface BoardMapper {
	
	/* 공지사항 게시글 작성 */
	public int noticeWriteArticle(BoardNoticeDto boardNoticeDto) throws SQLException;
	
	/* 공지사항 게시글 목록 */
	public List<BoardNoticeDto> noticeListArticle(BoardParameterDto boardParameterDto) throws SQLException;
	
	/* 공지사항 게시글 총 개수 */
	public int noticeGetTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	
	/* 공지사항 게시글 상세보기 */
	public BoardNoticeDto noticeGetArticle(int articleno) throws SQLException;
	
	/* 공지사항 조회수 */
	public void noticeUpdateHit(int articleno) throws SQLException;
	
	/* 공지사항 수정 */
	public int noticeModifyArticle(BoardNoticeDto boardNoticeDto) throws SQLException;
	
	/* 공지사항 삭제 */
	public int noticeDeleteArticle(int articleno) throws SQLException;
}