package com.koreait.sboard.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.UserEntity;

// 필요한 서비스들을 나열할 수 있게 하기 위해서 인터페이스를 만든다(이유: 확장성과 유연성)
@Mapper
public interface UserMapper {

	UserEntity selUser(UserEntity param);	// 사용자 정보의 값을 전부 가져오는 메서드.
	int insUser(UserEntity param);
}

/*
 UserMapper.xml에서 id가 메서드명이다(즉, xml에 id값이랑 메서드 명을 매핑 시켜줘야 한다.)
 UserMapper.xml에 의해서 Mybatis가 자동으로 DAO를 만들어주고, bean등록 까지 해준다..
*/