package com.koreait.sboard.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.BoardCmtDomain;
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
	
	// ---------------------------- Cmt ---------------------------- 
	
	int insertCmt(BoardCmtEntity param);	// 객체를 보냈기 때문에, 값을 맞춰줘야 한다.
	List<BoardCmtDomain> selCmtList(BoardCmtEntity param);	// int를 보내기 때문에 BoardMapper.xml에서 i_board 값을 아무거나 주면 된다.
	int deleteCmt(BoardCmtEntity param);
}
