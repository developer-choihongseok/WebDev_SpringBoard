package com.koreait.sboard.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.model.AuthDTO;
import com.koreait.sboard.model.AuthEntity;
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
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String join(UserEntity param) {
		service.insUser(param);
		return "redirect:/user/login";	// 스프링에서 sendRedirect 하는 방법.
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession hs) {
		hs.invalidate();
		
		return "redirect:/user/login";
	}
	
	@GetMapping("/findPw")
	public void findPw() {}
	
	@GetMapping("/findPwProc")
	public String findPwProc(AuthEntity param) {
		System.out.println("user_id : " + param.getUser_id());
		
		service.findPwProc(param);
		
		return "user/findPw";
	}
	
	@GetMapping("/findPwAuth")
	public void findPwAuth() {}
	
	@ResponseBody
	@PostMapping("/findPwAuth")
	public Map<String, Object> findPwAuth(@RequestBody AuthDTO param) {
		System.out.println("cd : " + param.getCd());
		System.out.println("user_id : " + param.getUser_id());
		System.out.println("user_pw : " + param.getUser_pw());
		
		Map<String, Object> rVal = new HashMap<String, Object>();
		rVal.put(Const.KEY_RESULT, service.findPwAuthProc(param));
		
		return rVal;
	}
}
