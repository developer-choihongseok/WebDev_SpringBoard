package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDTO;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardEntity;
import com.koreait.sboard.model.BoardParentDomain;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	public BoardParentDomain selBoardList(BoardDTO param){
		if(param.getTyp() == 0) {
			param.setTyp(1);
		}
		if(param.getRecordCntPerPage() == 0) {
			param.setRecordCntPerPage(5);
		}
		if(param.getPage() == 0) {
			param.setPage(1);
		}
		
		int sIdx = (param.getPage() - 1) * param.getRecordCntPerPage();
		param.setsIdx(sIdx);
		
		BoardParentDomain bpd = new BoardParentDomain();
		bpd.setMaxPageNum(mapper.selMaxPageNum(param));
		bpd.setList(mapper.selBoardList(param));
		bpd.setPage(param.getPage());
		bpd.setRecordCntPerPage(param.getRecordCntPerPage());	// 내가 몇 개 짜리 선택 했는지를 유지시키게 하기 위함.
		
		final int SIDE_NUM = Const.PAGE_SIDE_NUM;
		int pageLen = SIDE_NUM * 2;
		int page = param.getPage();	// 페이지 번호
		int maxPage = bpd.getMaxPageNum();
		
		int startPage = page - SIDE_NUM;
		int endPage = page + SIDE_NUM;
		
		if(pageLen < maxPage) {	// 나타내는 양쪽의 길이가 총 길이보다 작을 때..
			if(startPage < 1) {	// 나타내는 한곳의 길이가 현재 페이지보다 작을 때..
				startPage = 1;
			} else if(startPage > maxPage - pageLen) {	// 전체길이 - 나타내는 양쪽의 길이가 나타내는 페이지 왼쪽보다 클 때..
				startPage = maxPage - pageLen;			// 나타내는 곳의 왼쪽에는 총 페이지 - 나타내는 양쪽 길이 + 1을 넣어준다.
			}
			
			if(endPage > maxPage) {
				endPage = maxPage;
			}else if(endPage <= pageLen) {
				endPage = pageLen + 1;	// 나타내는 오른쪽에는 나타내는 양쪽의 페이지값을 넣어준다.
			}
		} else {
			startPage = 1;
			endPage = maxPage;
		}
		
		bpd.setStartPage(startPage);
		bpd.setEndPage(endPage);
		
		return bpd;
	}
	
	public int insertBoard(BoardEntity param) {
		return mapper.insertBoard(param);
	}
	
	public BoardDomain selBoard(BoardDTO param) {
		mapper.updateBoardHits(param);	// 조회 수 올리기
		
		return mapper.selBoard(param);
	}
	
	public int updateBoard(BoardEntity param) {
		return mapper.updateBoard(param);
	}
	
	public int deleteBoard(BoardDTO param) {
		return mapper.deleteBoard(param);
	}
	
	// ---------------------------- Cmt ---------------------------- 
	
	public int insertCmt(BoardCmtEntity param) {
		return mapper.insertCmt(param);	// 결과 값은 1 아니면 0.
	}
	
	public List<BoardCmtDomain> selCmtList(BoardCmtEntity param) {
		return mapper.selCmtList(param);
	}
	
	public int updateCmt(BoardCmtEntity param) {
		return mapper.updateCmt(param);
	}
	
	public int deleteCmt(BoardCmtEntity param) {
		return mapper.deleteCmt(param);
	}
}
