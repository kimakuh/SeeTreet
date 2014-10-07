package com.seetreet.bean;

public class GenreBean {
	private String category;
	private String detailGenre;
	
	public static final String KEY_CATEGORY = "genre_category";
	public static final String KEY_DETAIL = "genre_detail";
	
	public GenreBean(String category, String detailGenre) {
		// TODO Auto-generated constructor stub
		this.category = category;
		this.detailGenre = detailGenre;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDetailGenre() {
		return detailGenre;
	}
}
