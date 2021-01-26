package com.koreait.sboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.sboard.model.UserEntity;

/*
  Salt -> 원본 데이터의 앞 혹은 뒤에 다른 임의의 데이터를 끼워놓는 것을 말한다.
  		    그러므로, 완전히 다른 결과가 나오게 된다!
*/
public class SecurityUtils {
	// 보안과 관련 있는 것이기 때문에 Utils에 있던 isLogout(), getLoginUser()를  옮겼다.
	// true : 로그아웃 상태,	false : 로그인 상태
	public static boolean isLogout(HttpServletRequest request) {
		return getLoginUser(request) == null;
	}
		
	public static UserEntity getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
			
		return (UserEntity)session.getAttribute(Const.LOGINUSER);
	}
	
	// 실제로 수정, 삭제 할 때 자주 쓰인다!! -> 만들어 놓으면 편하다.
	// 글 작성자
	public static int getLoginUserPK(HttpServletRequest request) {
		UserEntity loginUser = getLoginUser(request);
		
		return loginUser == null ? 0 : loginUser.getI_user();
	}
	
	public static String gensalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
}
