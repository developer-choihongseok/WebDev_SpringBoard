package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public BoardEntity selBoard(BoardEntity param) {
		return mapper.selBoard(param);
	}
	
	public int insertBoard(BoardEntity param) {
		return mapper.insertBoard(param);
	}
	
	public int updateBoard(BoardEntity param) {
		return mapper.updateBoard(param);
	}
	
	public int deleteBoard(BoardEntity param) {
		return mapper.deleteBoard(param);
	}
}
