package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;

public interface BoardService {
	/* 공지사항 게시글 작성 */
	public boolean noticeWriteArticle(BoardNoticeDto boardNoticeDto) throws Exception;
	
	/* 공지사항 게시글 목록 */
	public List<BoardNoticeDto> noticeListArticle(BoardParameterDto boardParameterDto) throws Exception;
	//public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	
	/* 공지사항 게시글 상세보기 */
	public BoardNoticeDto noticeGetArticle(int articleno) throws Exception;
	
	/* 공지사항 조회수 */
	public void noticeUpdateHit(int articleno) throws Exception;
	
	/* 공지사항 수정 */
	public boolean noticeModifyArticle(BoardNoticeDto boardNoticeDto) throws Exception;
	
	/* 공지사항 삭제 */
	public boolean noticeDeleteArticle(int articleno) throws Exception;
	
}
