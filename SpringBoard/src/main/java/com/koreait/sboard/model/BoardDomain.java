package com.koreait.sboard.model;

public class BoardDomain extends BoardEntity{
	
	private String writer_nm;
	private String profile_img;
	private int favorite_cnt;
	private int is_favorite;	// 내가 좋아요 했는지 안했는지.
	
	public String getWriter_nm() {
		return writer_nm;
	}
	public void setWriter_nm(String writer_nm) {
		this.writer_nm = writer_nm;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public int getFavorite_cnt() {
		return favorite_cnt;
	}
	public void setFavorite_cnt(int favorite_cnt) {
		this.favorite_cnt = favorite_cnt;
	}
	public int getIs_favorite() {
		return is_favorite;
	}
	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}
}
