package com.koreait.sboard.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
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
		model.addAttribute(Const.KEY_LIST, service.selBoardList(param));
	}
	
	@GetMapping("/reg")
	public String reg() {	// 화면만 뿌리면 되므로, Model 필요 X.
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
		
		model.addAttribute(Const.KEY_DATA, service.selBoard(param));
	}
	
	@ResponseBody
	@DeleteMapping("/del/{i_board}")
	public Map<String, Object> del(BoardDTO param, HttpSession hs) {
		System.out.println("i_board: " + param.getI_board());
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 알아서 문자열 json 형태로 바꿔줄 것이다(즉, result = 0 또는 result = 1)
		map.put("result", service.deleteBoard(param));
		
		return map;
	}
	
	@GetMapping("/mod")
	public String mod(BoardDTO param, Model model) {
		model.addAttribute(Const.KEY_DATA, service.selBoard(param));
		return "board/regmod";
	}
	
	@PostMapping("/mod")
	public String mod(BoardEntity param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));	// 보안 처리를 해주면 아무나 수정 등을 X.
		service.updateBoard(param);
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}
	
	// ---------------------------- Cmt ---------------------------- 
	
	// @ResponseBody : JSON형태로 받을려면 꼭 이걸 써주어야 한다!!
	// @RequestBody : 받는 문자열이 JSON 형태라는것을 알려줘서, 알아서 문자열을 해석한다.
	@ResponseBody
	@PostMapping("/insCmt")
	public Map<String, Object> insCmt(@RequestBody BoardCmtEntity param, HttpSession hs){
		System.out.println("i_board : " + param.getI_board());
		System.out.println("ctnt: " + param.getCtnt());
		
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.insertCmt(param));
		
		return returnValue;
	}
	
	@ResponseBody
	@GetMapping("/cmtList")
	public List<BoardCmtDomain> selCmtList(@RequestParam int i_board){
		return service.selCmtList(i_board);
	}
	
}
