package com.koreait.sboard.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;	// mapper에 DAO가 들어온다.
	
	// 1 : 로그인 성공, 2 : 아이디 없음, 3 : 비밀번호 틀림
	public int login(UserEntity param, HttpSession hs) {
		UserEntity dbData = mapper.selUser(param);
		
		if(dbData == null) {
			return 2;	// 아이디 없음
		}
		
		String cryptLoginPw = SecurityUtils.hashPassword(param.getUser_pw(), dbData.getSalt());
		
		if(!cryptLoginPw.equals(dbData.getUser_pw())) {
			return 3;	// 비밀번호 틀림
		}
		
		dbData.setSalt(null);
		dbData.setUser_pw(null);
		
		hs.setAttribute(Const.KEY_LOGINUSER, dbData);
		
		return 1;	// 로그인 성공
	}
	
	public int insUser(UserEntity param) {
		String salt = SecurityUtils.gensalt();
		String encryptPw = SecurityUtils.hashPassword(param.getUser_pw(), salt);
		
		param.setSalt(salt);
		param.setUser_pw(encryptPw);

		return mapper.insUser(param);
	}
}
