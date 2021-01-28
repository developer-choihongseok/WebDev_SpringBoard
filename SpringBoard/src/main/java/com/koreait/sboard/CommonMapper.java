package com.koreait.sboard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.ManageBoardEntity;

@Mapper
public interface CommonMapper {
	List<ManageBoardEntity> selManageBoardList();
}
