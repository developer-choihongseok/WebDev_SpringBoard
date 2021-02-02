package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDTO;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardEntity;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	public List<BoardDomain> selBoardList(BoardDTO param){
		if(param.getTyp() == 0) {
			param.setTyp(1);
		}
		return mapper.selBoardList(param);
	}
	
	public int insertBoard(BoardEntity param) {
		return mapper.insertBoard(param);
	}
	
	public BoardDomain selBoard(BoardDTO param) {
		mapper.updateBoardHits(param);	// 조회  수 올리기
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
