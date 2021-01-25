package com.koreait.sboard.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.sboard.model.UserEntity;

@Controller
@RequestMapping("/user")	// 1차 주소값
public class UserController {
	@Autowired
	private UserService service;	// UserService의 기능들을 쓸 수 있다.
	
	@GetMapping("/login")	// 2차 주소값
	public void login() {}
	
	@PostMapping("/login")
	public String loginProc(UserEntity param, HttpSession hs) {
		int result = service.login(param, hs);
		
		if(result == 1) {
			return "redirect:/board/home";
		}
		return null;
	}
	
	@RequestMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity param) {
		service.insUser(param);
		return "redirect:/user/login";	// 스프링에서 sendRedirect 하는 방법.
	}
}
