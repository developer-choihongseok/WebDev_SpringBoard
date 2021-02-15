package com.koreait.sboard.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.AuthEntity;
import com.koreait.sboard.model.UserEntity;
import com.koreait.sboard.model.UserImgEntity;

// 필요한 서비스들을 나열할 수 있게 하기 위해서 인터페이스를 만든다.
@Mapper
public interface UserMapper {

	int insUser(UserEntity param);
	UserEntity selUser(UserEntity param);	// 사용자 정보의 값을 전부 가져오는 메서드.
	
	/* ------------------- 비밀번호, 프로필 이미지 변경 ------------------- */
	int updUser(UserEntity param);
	
	/* ------------------- 비밀번호 찾기 ------------------- */
	int insAuth(AuthEntity param);
	AuthEntity selAuth(AuthEntity param);
	int delAuth(AuthEntity param);
	
	/* ------------------- 프로필 이미지 삽입 ------------------- */
	int insertUserImg(UserImgEntity param);
	List<UserImgEntity> selUserImgList(UserEntity param);
}

/*
 UserMapper.xml에서 id가 메서드명이다(즉, xml에 id값이랑 메서드 명을 매핑 시켜줘야 한다.)
 UserMapper.xml에 의해서 Mybatis가 자동으로 DAO를 만들어주고, bean등록 까지 해준다.
*/