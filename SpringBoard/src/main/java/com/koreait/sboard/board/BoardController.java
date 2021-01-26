package com.koreait.sboard.board;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		service.insertBoard(param);
		
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}
	
}
