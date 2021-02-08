package com.koreait.sboard.model;

import java.util.List;

public class BoardParentDomain {
	
	private int maxPageNum;
	private List<BoardDomain> list;
	private int page;
	private int recordCntPerPage;
	
	public int getMaxPageNum() {
		return maxPageNum;
	}
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}
	public List<BoardDomain> getList() {
		return list;
	}
	public void setList(List<BoardDomain> list) {
		this.list = list;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRecordCntPerPage() {
		return recordCntPerPage;
	}
	public void setRecordCntPerPage(int recordCntPerPage) {
		this.recordCntPerPage = recordCntPerPage;
	}
}
