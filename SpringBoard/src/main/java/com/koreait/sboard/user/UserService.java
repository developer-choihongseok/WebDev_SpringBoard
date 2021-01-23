package com.koreait.sboard.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.model.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;	// mapper에 DAO가 들어온다.
	
	public List<UserEntity> selUserList(){
		return mapper.selUserList();
	}
	
	public void insUser(UserEntity param) {
		mapper.insUser(param);
	}
}
