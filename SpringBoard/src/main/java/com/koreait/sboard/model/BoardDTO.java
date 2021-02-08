package com.koreait.sboard.model;

public class BoardDTO extends BoardEntity{
	
	private int recordCntPerPage;	// 페이지 당 레코드 수
	private int sIdx;
	private int page;
	
	public int getRecordCntPerPage() {
		return recordCntPerPage;
	}
	public void setRecordCntPerPage(int recordCntPerPage) {
		this.recordCntPerPage = recordCntPerPage;
	}
	public int getsIdx() {
		return sIdx;
	}
	public void setsIdx(int sIdx) {
		this.sIdx = sIdx;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
