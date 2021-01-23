package com.koreait.sboard.common;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {
	
	public static String gensalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
	
	// 각 jsp파일 보여주기 위한 메소드.
	public static String myViewResolver(String fileNm) {
		return "/WEB-INF/views/" + fileNm + ".jsp";
	}
	
}
