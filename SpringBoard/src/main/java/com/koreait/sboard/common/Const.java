package com.koreait.sboard.common;

import java.util.List;

import com.koreait.sboard.model.ManageBoardEntity;

// Const 클래스 만든 이유 : 실수하는 것을 줄여준다.
public class Const {
	// 메모리 영역에 한번만 할당되고 계속 유지된다.
	public static List<ManageBoardEntity> menuList = null;
	
	public static final String KEY_LOGINUSER = "loginUser";
	public static final String KEY_LIST = "list";
	public static final String KEY_DATA = "data";
	public static final String KEY_RESULT = "result";
}
