package com.koreait.sboard.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.BoardDTO;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardEntity;

@Mapper
public interface BoardMapper {
	int insertBoard(BoardEntity param);
	List<BoardDomain> selBoardList(BoardDTO param);
	BoardDomain selBoard(BoardDTO param);
	int deleteBoard(BoardDTO param);
	int updateBoard(BoardEntity param);
}
