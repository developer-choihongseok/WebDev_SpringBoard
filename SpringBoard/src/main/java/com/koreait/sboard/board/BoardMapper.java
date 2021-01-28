package com.koreait.sboard.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDTO;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardEntity;

@Mapper
public interface BoardMapper {
	List<BoardDomain> selBoardList(BoardDTO param);
	
	int insertBoard(BoardEntity param);
	BoardDomain selBoard(BoardDTO param);	// 읽기
	int updateBoard(BoardEntity param);
	int updateBoardHits(BoardDTO param);
	int deleteBoard(BoardDTO param);
	
	int insertCmt(BoardCmtEntity param);
}
