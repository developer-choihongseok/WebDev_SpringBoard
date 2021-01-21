package com.koreait.sboard.common;

public class Utils {
	// 각 jsp파일 보여주기 위한 메소드.
	public static String myViewResolver(String fileNm) {
		return "/WEB-INF/views/" + fileNm + ".jsp";
	}
	
	
}
