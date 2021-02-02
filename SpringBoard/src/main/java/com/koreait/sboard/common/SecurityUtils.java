package com.koreait.sboard.common;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.sboard.model.UserEntity;

/*
  Salt -> 원본 데이터의 앞 혹은 뒤에 다른 임의의 데이터를 끼워놓는 것을 말한다.
  		    그러므로, 완전히 다른 결과가 나오게 된다!
*/
public class SecurityUtils {
	// true : 로그인 된 상태,	 false : 로그아웃 된 상태
	public static boolean isLogin(HttpSession hs) {
		return getLoginUser(hs) != null;
	}
		
	public static UserEntity getLoginUser(HttpSession hs) {
		return (UserEntity)hs.getAttribute(Const.KEY_LOGINUSER);
	}
	
	// 실제로 수정, 삭제 할 때 자주 쓰인다!! -> 만들어 놓으면 편하다.
	// 글 작성자
	public static int getLoginUserPK(HttpSession hs) {
		UserEntity loginUser = getLoginUser(hs);
		
		return loginUser == null ? -1 : loginUser.getI_user();
	}
	
	public static String gensalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
	
	// 인증메일을 받기 위한 랜덤한 숫자 생성.
	public static String getPrivateCode(int length) {
		String str = "";
		
		for (int i = 0; i < length; i++) {
			str += (int)(Math.random() * 10);
		}
		return str;
	}
}
