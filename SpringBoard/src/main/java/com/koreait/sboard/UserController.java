package com.koreait.sboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.sboard.common.Utils;

@Controller
@RequestMapping("/user")	// 1차 주소값
public class UserController {
	
	@GetMapping("/login")	// 2차 주소값
	public void login(Model model) {
		
	}
	
	@RequestMapping("/join")
	public void join(Model model) {
		
	}
	
	// 아래 2개는 템플릿을 사용하여야 하기 떄문에, 사용할 수 X. -> 참고하기.
//	@GetMapping("/login")
//	public void login() {
//		
//	}
//	
//	@RequestMapping("/join")
//	public void join() {
//		
//	}
}
