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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		model.addAttribute(Const.KEY_DATA, service.selBoardList(param));
	}
	
	@GetMapping("/reg")
	public String reg() {	// 화면만 뿌리면 되므로, Model 필요 X.
		return "board/regmod";
	}
	
	@PostMapping("/reg")
	public String reg(BoardEntity param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));	// 보안 처리를 해주면 아무나 등록,수정 등을 X.
		
		service.insertBoard(param);
		
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}
	
	@GetMapping("/detail")
	public void detail(BoardDTO param, HttpSession hs, Model model) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		model.addAttribute(Const.KEY_DATA, service.selBoard(param));
	}
	
	@ResponseBody
	@DeleteMapping("/del/{i_board}")	// i_board 값으로 데이터를 삭제.
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
		param.setI_user(SecurityUtils.getLoginUserPK(hs));	// 보안 처리를 해주면 아무나 등록,수정 등을 X.
		service.updateBoard(param);
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}
	
	// ---------------------------- Cmt ---------------------------- 
	
	// @ResponseBody : 요청한 형태에 맞춰서 메시지 변환기를 통해 결과값을 반환한다. @RequestBody가 선택한 형식으로 결과값을 변환하여 반환한다고 보면 된다.
	// @RequestBody : HTTP 요청 본문에 담긴 값들을 자바 객체로 변환 시켜, 객체에 저장시킨다.
	@ResponseBody
	@PostMapping("/insCmt")
	public Map<String, Object> insCmt(@RequestBody BoardCmtEntity param, HttpSession hs){
//		System.out.println("i_board : " + param.getI_board());
//		System.out.println("ctnt: " + param.getCtnt());
		
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.insertCmt(param));
		
		return returnValue;
	}
	
	@ResponseBody
	@GetMapping("/cmtList")
	public List<BoardCmtDomain> selCmtList(BoardCmtEntity param, HttpSession hs){
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		return service.selCmtList(param);
	}
	
	@ResponseBody
	@PutMapping("/updCmt")
	public Map<String, Object> updCmt(@RequestBody BoardCmtEntity param, HttpSession hs){
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.updateCmt(param));
		
		return returnValue;
	}
	
	@ResponseBody
	@DeleteMapping("/delCmt")
	public Map<String, Object> delCmt(BoardCmtEntity param, HttpSession hs){
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.deleteCmt(param));
		
		return returnValue;
	}
}
