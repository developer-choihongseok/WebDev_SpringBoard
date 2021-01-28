package com.koreait.sboard.board;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.BoardDTO;
import com.koreait.sboard.model.BoardEntity;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("/home")
	public void home() {}
	
	@GetMapping("/list")
	public void list(Model model, BoardDTO param){
		model.addAttribute("list", service.selBoardList(param));
	}
	
	@GetMapping("/reg")
	public String reg() {
		return "board/regmod";
	}
	
	@PostMapping("/reg")
	public String reg(BoardEntity param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		service.insertBoard(param);
		
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}
	
	@GetMapping("/detail")
	public void detail(BoardDTO param, HttpSession hs, Model model) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		model.addAttribute("data", service.selBoard(param));
	}
	
	// RESTful : 주소값으로 더 보낼수 있다.
	@ResponseBody
	@GetMapping("/del/{i_board}")
	public Map<String, Object> del(BoardDTO param, HttpSession hs) {
		System.out.println("i_board: " + param.getI_board());
		
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 알아서 문자열 json 형태로 바꿔줄 것이다(즉, result = 0 또는 result = 1)
		map.put("result", service.deleteBoard(param));
		
		return map;
	}
}
