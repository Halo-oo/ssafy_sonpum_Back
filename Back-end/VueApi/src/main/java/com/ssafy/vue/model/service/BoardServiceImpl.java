package com.ssafy.vue.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardNoticeDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;

	/* 공지사항 게시글 작성 */
	@Override
	public boolean noticeWriteArticle(BoardNoticeDto boardNoticeDto) throws Exception {
		if(boardNoticeDto.getSubject() == null || boardNoticeDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).noticeWriteArticle(boardNoticeDto) == 1;
	}

	/* 공지사항 게시글 목록 */
	@Override
	public List<BoardNoticeDto> noticeListArticle(BoardParameterDto boardParameterDto) throws Exception {
		//int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		//boardParameterDto.setStart(start);
		
		return sqlSession.getMapper(BoardMapper.class).noticeListArticle(boardParameterDto);
	}
//	@Override
//	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
//		int naviSize = 5;
//		PageNavigation pageNavigation = new PageNavigation();
//		pageNavigation.setCurrentPage(boardParameterDto.getPg());
//		pageNavigation.setNaviSize(naviSize);
//		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);//총글갯수  269
//		pageNavigation.setTotalCount(totalCount);  
//		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = boardParameterDto.getPg() <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//		return pageNavigation;
//	}

	/* 공지사항 게시글 상세보기 */
	@Override
	public BoardNoticeDto noticeGetArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).noticeGetArticle(articleno);
	}
	
	/* 공지사항 조회수 */
	@Override
	public void noticeUpdateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).noticeUpdateHit(articleno);
	}
	
	/* 공지사항 수정 */
	@Override
	@Transactional
	public boolean noticeModifyArticle(BoardNoticeDto boardNoticeDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).noticeModifyArticle(boardNoticeDto) == 1;
	}
	
	/* 공지사항 삭제 */
	@Override
	@Transactional
	public boolean noticeDeleteArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).noticeDeleteArticle(articleno) == 1;
	}
}